package com.pemwa.topnews.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pemwa.topnews.database.getDatabaseInstance
import com.pemwa.topnews.repository.ArticlesRepository
import retrofit2.HttpException

/**
 * Using WorkManager to pre-fetch data when the app is in the background.
 * This ensures that users will get the freshest data every time they open the app.
 * WorkManager will make sure to schedule the work so it has the lowest impact on battery life possible.
 *
 * We use a CoroutineWorker, because we want to use coroutines to handle our asynchronous code and threading.
 */
class RefreshDataWorker(
    appContext: Context,
    params: WorkerParameters
): CoroutineWorker(appContext, params) {

    /**
     * This is where our [RefreshDataWorker] does its work i.e syncing with the network.
     */
    override suspend fun doWork(): Result {
        val database = getDatabaseInstance(applicationContext)
        val repository = ArticlesRepository(database)

        // Here we return SUCCESS or RETRY
        return try {
            repository.refreshArticles()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

    /**
     * Creating a unique identifier for our work
     */
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }
}
