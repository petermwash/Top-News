package com.pemwa.topnews.view.overview

import androidx.recyclerview.widget.RecyclerView
import com.pemwa.topnews.domain.Article

/**
 * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Article]
 * associated with the current item to the [onItemClick] function.
 * @param itemClickListener lambda that will be called with the current [Article]
 */
class OnItemClickListener(val itemClickListener: (newsItem: Article) -> Unit) {
    fun onItemClick(newsItem: Article) = itemClickListener(newsItem)
}