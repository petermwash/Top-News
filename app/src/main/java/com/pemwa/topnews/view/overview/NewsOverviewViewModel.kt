package com.pemwa.topnews.view.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.tabs.TabLayout
import com.pemwa.topnews.domain.Article
import com.pemwa.topnews.network.Network
import com.pemwa.topnews.repository.ArticlesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class NewsOverviewViewModel : ViewModel() {

    // Obsevabal fied to keep track of the selected tab
    private val _everythingTabSected =  MutableLiveData<Boolean>()
    val everythingTabSected : LiveData<Boolean>
        get() = _everythingTabSected

    /**
     * A list of all news articles that can be shown on the screen.
     */
    private val _newsArticleList = MutableLiveData<List<Article>>()

    // The external immutable LiveData for the newsArticleList String
    val newsArticleList: LiveData<List<Article>>
        get() = _newsArticleList

    // The list of city choices
    private var _cityList = MutableLiveData<List<String>>()
    val cityList: LiveData<List<String>>
        get() = _cityList

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
        _cityList.value = listOf("Nairobi", "Kampala", "Lagos", "New York", "Kigali")
    }

    fun getTopHeadlines() {
        _everythingTabSected.value = false
        coroutineScope.launch {
            var getTopHeadlines = Network.news.getTopHeadlinesAsync("us")
            try {
                // Await the completion of Retrofit request
                val listResult = getTopHeadlines.await()
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
        _everythingTabSected.value = true
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            val getNewsItemsDeferred = Network.news.getEverythingAsync("us")
            try {
                // Await the completion of our Retrofit request
                val listResult = getNewsItemsDeferred.await()
                _newsArticleList.value = listResult.articles
            } catch (e: Exception) {
                Timber.e("Failure: ${e.message}")
            }
        }
    }

    /**
     * A method to initiate the tab navigation
     */
    fun selectTheCorrectTab(tabLayout: TabLayout) {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> if (!everythingTabSected.value!!) getAllNewsItems()
                    1 -> getTopHeadlines()
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //
            }


        })
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}