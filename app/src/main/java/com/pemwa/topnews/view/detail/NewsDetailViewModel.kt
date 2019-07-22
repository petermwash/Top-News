package com.pemwa.topnews.view.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.pemwa.topnews.domain.Article

class NewsDetailViewModel(newsArticle: Article, app: Application) : AndroidViewModel(app) {

    // The internal MutableLiveData Article that stores the SelectedArticle on clicking an item
    // And the external immutable LiveData for accessing the SelectedArticle
    private val _selectedArticle = MutableLiveData<Article>()
    val selectedArticle: LiveData<Article>
        get() = _selectedArticle

    // Initialize the _selectedArticle MutableLiveData
    init {
        _selectedArticle.value = newsArticle
    }

}
