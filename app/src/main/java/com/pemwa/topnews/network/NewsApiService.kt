package com.pemwa.topnews.network

import com.pemwa.topnews.domain.News
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A retrofit service to fetch the news articles.
 */
interface NewsApiService {

    @GET("everything")
    fun getEverythingAsync(@Query("q")location: String): Deferred<NetworkArticleContainer>

    @GET("top-headlines")
    fun getTopHeadlinesAsync(@Query("q")location: String): Deferred<NetworkArticleContainer>
}
