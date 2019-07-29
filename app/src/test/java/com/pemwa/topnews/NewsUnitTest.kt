package com.pemwa.topnews

import com.pemwa.topnews.domain.Source
import com.pemwa.topnews.util.TestUtils
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 *  Contains the unit test for news article.
 */
class NewsUnitTest {

    private val status = "OK"
    private val totalResults = 100
    private val source = Source("id","name")
    private val author = "author"
    private val title = " title"
    private val description = "description"
    private val url = "url"
    private val urlToImage = "urlToImage"
    private val publishedAt = "publishedAt"
    private val content = "content"

    private val article = TestUtils.createArticle()
    private val newsObject = TestUtils.createNewsItem()

    @Test
    fun testNewsData() {
        assertEquals(status, newsObject.status)
        assertEquals(totalResults, newsObject.totalResults)
        assertEquals(listOf(article), newsObject.articles)
    }

    @Test
    fun testArticleData() {
        assertEquals(source, article.source)
        assertEquals(author, article.author)
        assertEquals(title, article.title)
        assertEquals(description, article.description)
        assertEquals(url, article.url)
        assertEquals(urlToImage, article.urlToImage)
        assertEquals(publishedAt, article.publishedAt)
        assertEquals(content, article.content)
    }
}