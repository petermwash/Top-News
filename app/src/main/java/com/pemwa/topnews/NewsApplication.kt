package com.pemwa.topnews

import android.app.Application
import timber.log.Timber

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

        // Setting up Timber for logging
        Timber.plant(Timber.DebugTree())
    }
}