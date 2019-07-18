package com.pemwa.topnews.view.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pemwa.topnews.domain.Article
import com.pemwa.topnews.network.Network
import com.pemwa.topnews.repository.ArticlesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class NewsOverviewViewModel : ViewModel() {

    /**
     * A list of all news articles that can be shown on the screen.
     */
    private val _resultStatus =  MutableLiveData<String>()
    val resultStatus : LiveData<String>
        get() = _resultStatus

    // The internal MutableLiveData String that stores the most recent newsArticleList
    private val _newsArticleList = MutableLiveData<List<Article>>()

    // The external immutable LiveData for the newsArticleList String
    val newsArticleList: LiveData<List<Article>>
        get() = _newsArticleList

//    /**
//     * Creating an instance of the repository.
//     */
//    private val articlesRepository = ArticlesRepository()


    // Creating a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    /**
     * Call getNewsItems() on init so we can display status immediately.
     */
    init {
        getAllNewsItems()
    }

    fun getTopHeadlines() {
        coroutineScope.launch {
            var getTopHeadlines = Network.news.getTopHeadlinesAsync("us")
            try {
                // Await the completion of Retrofit request
                val listResult = getTopHeadlines.await()
                _resultStatus.value = "Success ${listResult.totalResults} Articles retrieved!!"
                _newsArticleList.value = listResult.articles
            } catch (e: Exception) {
                Timber.e("Failure: ${e.message}")
            }
        }
    }

    /**
     * Sets the value of the newsArticleList LiveData to the NewsApi status or the successful number of
     * News items retrieved.
     */
    fun getAllNewsItems() {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getNewsItemsDeferred = Network.news.getEverythingAsync("us")
            try {
                // Await the completion of our Retrofit request
                val listResult = getNewsItemsDeferred.await()
                _resultStatus.value = "Success ${listResult.totalResults} Articles retrieved!!"
                _newsArticleList.value = listResult.articles
            } catch (e: Exception) {
                Timber.e("Failure: ${e.message}")
            }
        }
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}