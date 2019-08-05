package com.pemwa.topnews

import android.app.Application
import android.content.ContextWrapper
import androidx.work.*
import com.pemwa.topnews.util.ConnectivityStatus
import com.pemwa.topnews.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * A variable to store an instance of the application
 */
private lateinit var INSTANCE: Application

/**
 * Override application to setup background work via WorkManager
 */
class NewsApplication : Application() {

    /**
     * onCreate() is called before the first screen is shown to the user.
     *
     * We use it to setup any background tasks, running expensive setup operations in a background
     * thread to avoid delaying app start.
     */
    override fun onCreate() {
        super.onCreate()

        //Assigning instance of the application
        INSTANCE = this

        // Observing the network status
        ConnectivityStatus().observeForever {
            isNetworkConnected = it ?: false
        }


        // Setting up Timber for logging
        Timber.plant(Timber.DebugTree())

        delayedInit()
    }

    /**
     * A coroutine scope to use for the application
     */
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    /**
     * An initialization function that does not block the main thread.
     * It runs the [setupRecurringWork] in the coroutine.
     */
    private fun delayedInit() = applicationScope.launch {
        setupRecurringWork()
    }

    /**
     * Making a PeriodWorkRequest for the RefreshDataWorker.
     * It runs only once every day and with the defined constraints.
     */
    private fun setupRecurringWork() {

        // Using a Builder to define constraints.
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .setRequiresDeviceIdle(true)
            .build()

        // Set up and schedule a PeriodicWorkRequest
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        // Schedule the work as unique
        WorkManager.getInstance()
            .enqueueUniquePeriodicWork(
                RefreshDataWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest
            )
    }

    companion object {
        var isNetworkConnected = false
    }
}

/**
 * An object from which we cann access the instance of the application
 */
object AppContext : ContextWrapper(INSTANCE)
