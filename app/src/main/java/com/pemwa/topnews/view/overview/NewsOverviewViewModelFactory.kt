package com.pemwa.topnews.view.overview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsOverviewViewModelFactory(
    val app: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsOverviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsOverviewViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}
