package com.pemwa.topnews.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pemwa.topnews.database.ArticlesDatabase
import com.pemwa.topnews.database.asEverythingDomainModel
import com.pemwa.topnews.database.asTopHeadlinesDomainModel
import com.pemwa.topnews.domain.Article
import com.pemwa.topnews.network.Network
import com.pemwa.topnews.network.asDatabaseEverytingModel
import com.pemwa.topnews.network.asDatabaseTopHeadlinesModel
import com.pemwa.topnews.util.isNetworkConnected
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
class ArticlesRepository(private val database: ArticlesDatabase) {

    /**
     * A list of all news articles that can be shown on the screen.
     */
    val articlesEverything: LiveData<List<Article>> = Transformations.map(database.articlesDao.getFromEverything()) {
        it.asEverythingDomainModel()
    }

    /**
     * A list of top-headlines news articles that can be shown on the screen.
     */
    val articlesTopHeadlines: LiveData<List<Article>> = Transformations.map(database.articlesDao.getFromTopHeadlines()) {
        it.asTopHeadlinesDomainModel()
    }

    var locationFilter: String = ""

    suspend fun getFilters(filter: String) {
        if (isNetworkConnected()) {
            locationFilter = filter
            refreshArticles()
        }
    }

    /**
     * Defining a "refresh" function to update the offline cache.
     * We make it a "suspend function" since it will be called from a coroutine.
     *
     * This function uses the IO dispatcher to ensure that the insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     */
    suspend fun refreshArticles() {

        /**
         * Run on the IO Dispatcher
         */
        withContext(Dispatchers.IO) {

            /**
             * Making network calls to get articles, and we use the await() function to tell the coroutine
             * to suspend until the data is available.
             */
            val everything = Network.news.getEverythingAsync(locationFilter).await()
            val topHeadlines = Network.news.getTopHeadlinesAsync(locationFilter).await()

            /**
             * We clear the old data from our tables then insert the new data into the database
             * The asterisk * is the spread operator. It allows us to pass in an array to a function that expects varargs.
             */
            database.articlesDao.clearEverything()
            database.articlesDao.clearTopHeadlines()
            database.articlesDao.insertIntoEverything(*everything.asDatabaseEverytingModel())
            database.articlesDao.insertIntoTopHeadlines(*topHeadlines.asDatabaseTopHeadlinesModel())

        }
    }
}