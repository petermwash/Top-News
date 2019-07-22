package com.pemwa.topnews.view.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pemwa.topnews.domain.Article

/**
 * Simple ViewModel factory that provides the [Article] and context to the ViewModel.
 */
class NewsDetailViewModelFactory(
    private val newsArticle: Article,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java)) {
            return NewsDetailViewModel(newsArticle, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}