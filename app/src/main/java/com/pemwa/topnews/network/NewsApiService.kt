package com.pemwa.topnews.network

import com.pemwa.topnews.domain.Article
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A retrofit service to fetch the news articles.
 */
interface NewsApiService {

    @GET("everything")
    fun getEverythingAsync(@Query("q")location: String):
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<Article>

    @GET("top-headlines")
    fun getTopHeadlinesAsync(@Query("country")location: String):
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<Article>
}
