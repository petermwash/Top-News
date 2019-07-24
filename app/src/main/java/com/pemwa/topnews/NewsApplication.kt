package com.pemwa.topnews

import android.app.Application
import android.content.ContextWrapper
import com.pemwa.topnews.util.ConnectivityStatus
import timber.log.Timber

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
    }

    companion object {
        var isNetworkConnected = false
    }
}

/**
 * An object from which we cann access the instance of the application
 */
object AppContext : ContextWrapper(INSTANCE)
