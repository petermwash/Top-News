package com.pemwa.topnews.network

import com.pemwa.topnews.database.DatabaseEverything
import com.pemwa.topnews.database.DatabaseTopHeadlines
import com.pemwa.topnews.domain.Source
import com.squareup.moshi.JsonClass

/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. We are converting these to domain objects before
 * using them.
 */

/**
 * ArticlesHolder holds a list of articles.
 *
 * This is to parse first level of our network result which looks like
 *
 * {
 * "status": "ok",
 * "totalResults": Int,
 * "articles": []
 * }
 *
 */
@JsonClass(generateAdapter = true)
data class NetworkArticleContainer(val articles: List<NetworkArticle>)


/**
 * A data class defining the [NetworkArticle] fetched from the network.
 */
@JsonClass(generateAdapter = true)
data class NetworkArticle(
    val source: Source?,
    val author: String? = "Unknown",
    val title: String? = "Unknown",
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String? = "Unknown",
    val content: String?
)

/**
 * An extension function that converts from data transfer objects to database objects
 */
fun NetworkArticleContainer.asDatabaseEverytingModel() : Array<DatabaseEverything> {
    return articles.map {
        DatabaseEverything(
            source = it.source,
            author = it.author ?: "Unknown",
            title = it.title ?: "Unknown",
            description = it.description,
            url = it.url ?: "Unknown",
            urlToImage = it.urlToImage,
            publishedAt = it.publishedAt ?: "Unknown",
            content = it.content
        )
    }.toTypedArray()
}

/**
 * An extension function that converts from data transfer objects to database objects
 */
fun NetworkArticleContainer.asDatabaseTopHeadlinesModel() : Array<DatabaseTopHeadlines> {
    return articles.map {
        DatabaseTopHeadlines(
            source = it.source,
            author = it.author ?: "Unknown",
            title = it.title ?: "Unknown",
            description = it.description,
            url = it.url ?: "Unknown",
            urlToImage = it.urlToImage,
            publishedAt = it.publishedAt ?: "Unknown",
            content = it.content
        )
    }.toTypedArray()
}
