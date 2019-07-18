package com.pemwa.topnews.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pemwa.topnews.domain.Article
import com.pemwa.topnews.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching News articles from the network and storing them on the disk/db.
 *
 * A Repository is just a regular class that has one (or more) methods that load data
 * without specifying the data source as part of the main API.
 *
 * The repository hides the complexity of managing the interactions between the database and the networking code.
 */
class ArticlesRepository {

    /**
     * Defining a "refresh" function to refresh the offline cache.
     * We make it a "suspend function" since it will be called from a coroutine.
     *
     * This function uses the IO dispatcher to ensure that the insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     */
    suspend fun refreshArticles() {

        // Run on the IO Dispatcher
        withContext(Dispatchers.IO) {

            // Making network calls to get articles, and we use the await() function to tell the coroutine
            // to suspend until the data is available.

        }
    }
}