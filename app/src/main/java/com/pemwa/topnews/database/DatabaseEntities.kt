package com.pemwa.topnews.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pemwa.topnews.domain.Article
import com.pemwa.topnews.domain.Source

/**
 * A data class defining the [DatabaseEverything] object/entity/database_table
 */
@Entity(tableName = "articles_everything")
data class DatabaseEverything constructor(
    val source: Source?,
    val author: String = "Unknown",
    val title: String = "Unknown",
    val description: String?,
    @PrimaryKey
    val url: String,
    val urlToImage: String?,
    val publishedAt: String = "Unknown",
    val content: String?
)

/**
 * A data class defining the [DatabaseTopHeadlines] object/entity/database_table
 */
@Entity(tableName = "articles_top_headlines")
data class DatabaseTopHeadlines constructor(
    val source: Source?,
    val author: String = "Unknown",
    val title: String = "Unknown",
    val description: String?,
    @PrimaryKey
    val url: String,
    val urlToImage: String?,
    val publishedAt: String = "Unknown",
    val content: String?
)

/**
 * An extension function which converts from database objects to domain objects
 */
fun List<DatabaseEverything>.asEverythingDomainModel() : List<Article> {
    return map {
        Article(
            source = it.source,
            author = it.author,
            title = it.title,
            description = it.description,
            url = it.url,
            urlToImage = it.urlToImage,
            publishedAt = it.publishedAt,
            content = it.content
        )
    }
}

/**
 * An extension function which converts from database objects to domain objects
 */
fun List<DatabaseTopHeadlines>.asTopHeadlinesDomainModel() : List<Article> {
    return map {
        Article(
            source = it.source,
            author = it.author,
            title = it.title,
            description = it.description,
            url = it.url,
            urlToImage = it.urlToImage,
            publishedAt = it.publishedAt,
            content = it.content
        )
    }
}

