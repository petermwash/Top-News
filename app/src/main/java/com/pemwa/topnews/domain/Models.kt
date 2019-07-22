package com.pemwa.topnews.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 *
 * @see database for objects that are mapped to the database
 * @see network for objects that parse or prepare network calls
 */

/**
 * Article represent an article that can be displayed.
 */
@Parcelize
data class Article(val source: Source?,
                   val author: String?,
                   val title: String?,
                   val description: String?,
                   val url: String?,
                   val urlToImage: String?,
                   val publishedAt: String?,
                   val content: String?) : Parcelable

/**
 * Source represent a source object for an article.
 */
@Parcelize
data class  Source(val id: String?,
                   val name: String?) : Parcelable

/**
 * News object that represent a News Item.
 */
@Parcelize
data class  News(val status: String,
                 val totalResults: Int = 0,
                 val articles: List<Article>?) : Parcelable


