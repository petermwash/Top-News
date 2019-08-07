package com.pemwa.topnews.util

import com.pemwa.topnews.database.DatabaseEverything
import com.pemwa.topnews.database.DatabaseTopHeadlines
import com.pemwa.topnews.domain.Article
import com.pemwa.topnews.domain.News
import com.pemwa.topnews.domain.Source

class TestUtils {

    companion object {
        private val status = "OK"
        private val totalResults = 100
        private val source = Source("id","name")
        private val author = "author"
        private val title = " title"
        private val description = "description"
        private val url = "https://www.google.co.ke"
        private val urlToImage = "https://www.google.co.ke/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"
        private val publishedAt = "2019-08-08T08:00:00Z"
        private val content = "content"

        fun createArticle() : Article {
            return Article(source,author,title,description,url,urlToImage,publishedAt,content)
        }

        fun createNewsItem() : News {
            return News(status,totalResults, listOf(createArticle()))
        }

        fun createArticleDatabaseEverything() : DatabaseEverything {
            return DatabaseEverything(source,author,title,description,url,urlToImage,publishedAt,content)
        }

        fun createArticleDatabaseTopHeadlines(): DatabaseTopHeadlines {
            return DatabaseTopHeadlines(source,author,title,description,url,urlToImage,publishedAt,content)
        }
    }
}