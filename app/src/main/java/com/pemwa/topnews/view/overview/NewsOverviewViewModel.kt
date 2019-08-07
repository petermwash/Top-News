package com.pemwa.topnews.view.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.tabs.TabLayout
import com.pemwa.topnews.database.getDatabaseInstance
import com.pemwa.topnews.domain.Article
import com.pemwa.topnews.repository.ArticlesRepository
import com.pemwa.topnews.util.isNetworkConnected
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NewsOverviewViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Observable field to keep track of the selected tab
     */
    private val _everythingTabSelected = MutableLiveData<Boolean>()
    val everythingTabSelected: LiveData<Boolean>
        get() = _everythingTabSelected

    /**
     * Observable field to keep track of the selected tab
     */
    private val _topHeadlinesTabSelected = MutableLiveData<Boolean>()
    val topHeadlinesTabSelected: LiveData<Boolean>
        get() = _topHeadlinesTabSelected

    /**
     * Observable field to keep track of the tab navigation
     */
    private val _tabNavigated = MutableLiveData<Boolean>()
    val tabNavigated: LiveData<Boolean>
        get() = _tabNavigated

    /**
     * A list of all news articles that can be shown on the screen.
     */
    val newsArticleList = MutableLiveData<List<Article>>()

    /**
     * Encapsulated LiveData variable for navigating to the selectedArticle detail screen
     */
    private val _navigateToSelectedArticle = MutableLiveData<Article>()
    val navigateToSelectedArticle: LiveData<Article>
        get() = _navigateToSelectedArticle

    /**
     * A database variable to hold the singleton instance of our db
     */
    private val database = getDatabaseInstance(application)

    /**
     * Creating an instance of the repository.
     */
    private val articlesRepository = ArticlesRepository(database)


    /**
     * Creating a Coroutine scope using a job to be able to cancel when needed
     */
    private var viewModelJob = SupervisorJob()

    /**
     * The Coroutine runs using the Main (UI) dispatcher
     */
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var tabPosition: Int? = 0

    /**
     * Call getNewsItems() on init so we can display status immediately.
     */
    init {
        if (isNetworkConnected()){
            coroutineScope.launch {
                articlesRepository.refreshArticles()
            }
        }
        getAllNewsItems()
    }

    /**
     * Creating a list of news articles from the repository
     */
    val everything = articlesRepository.articlesEverything
    val topHeadlines = articlesRepository.articlesTopHeadlines

    /**
     * Updates the value of the selected tab
     */
    fun getTopHeadlines() {
        _tabNavigated.value = true
        _everythingTabSelected.value = false
        _topHeadlinesTabSelected.value = true
    }

    /**
     * Updates the value of the selected tab
     */
    fun getAllNewsItems() {
        _tabNavigated.value = true
        _topHeadlinesTabSelected.value = false
        _everythingTabSelected.value = true
    }

    /**
     * A method to initiate the tab navigation
     */
    fun selectTheCorrectTab(tabLayout: TabLayout) {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabPosition = tab?.position
                when (tab?.position) {
                    0 -> getAllNewsItems()
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
     * When a news item is clicked, set the [_navigateToSelectedArticle] [MutableLiveData]
     * @param article The [Article] that was clicked on.
     */
    fun displayArticleDetails(article: Article) {
        _navigateToSelectedArticle.value = article
    }

    /**
     * After the navigation has taken place, [_navigateToSelectedArticle] is set to null
     */
    fun displayArticleDetailsComplete() {
        _navigateToSelectedArticle.value = null
    }

    /**
     * Complete tab navigation
     */
    fun tabNavigatedDone() {
        _tabNavigated.value = false
    }

    /**
     * Update the  city filter
     */
    fun onCityFilterChanged(filter: String, isChecked: Boolean) {
        if (isChecked) {
            when(filter) {
                "New York" -> coroutineScope.launch { articlesRepository.getFilters("United States") }
                "Lagos" -> coroutineScope.launch { articlesRepository.getFilters("Nigeria") }
                "Nairobi" -> coroutineScope.launch { articlesRepository.getFilters("Kenya") }
                "Kampala" -> coroutineScope.launch { articlesRepository.getFilters("Uganda") }
                "Kigali" -> coroutineScope.launch { articlesRepository.getFilters("Rwanda") }
            }
            if (tabPosition == 0) {
                getAllNewsItems()
            } else {
                getTopHeadlines()
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