package com.pemwa.topnews

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pemwa.topnews.domain.Article
import com.pemwa.topnews.util.DateUtils
import com.pemwa.topnews.view.overview.NewsOverviewAdapter

/**
 * Binding adapter to bind the recyclerView adapter(news item list)
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Article>?) {
    val adapter = recyclerView.adapter as NewsOverviewAdapter
    adapter.submitList(data) {
        // scroll the list to the top after the diffs are calculated and posted
        recyclerView.scrollToPosition(0)
    }
}

/**
 * Binding adapter to hide or display a view
 */
@BindingAdapter("showOnlyWhenEmpty")
fun View.showOnlyWhenEmpty(data: List<Article>?) {
    visibility = when {
        data == null || data.isEmpty() -> View.VISIBLE
        else -> View.GONE
    }
}

/**
 * A BindingAdapter to convert imgUrl to a URI with the https scheme.
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri()
            .buildUpon()
            .scheme("https")
            .build()

        // Using Glide to download the image and display it in imgView:
        Glide.with(imgView.context)
            .load(imgUri)
            // Adding the requestOptions for placeholder and error into the Glide fluent call
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

/**
 * A BindingAdapter to convert a date to [SimpleDateFormat] for display
 */
@BindingAdapter("datePublished")
fun TextView.setDatePublished(date: String) {
    date.let {
        text = DateUtils.convertToSimpleDateFormat(it)
    }
}
