package com.pemwa.topnews

import androidx.fragment.app.Fragment
import com.pemwa.topnews.database.*
import com.pemwa.topnews.domain.Source
import com.pemwa.topnews.network.NetworkArticle
import com.pemwa.topnews.network.NetworkArticleContainer
import com.pemwa.topnews.network.asDatabaseEverytingModel
import com.pemwa.topnews.network.asDatabaseTopHeadlinesModel
import com.pemwa.topnews.util.DateUtils
import com.pemwa.topnews.util.TestUtils
import com.pemwa.topnews.view.isNetworkConnected
import com.pemwa.topnews.view.showErrorSnackBar
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mock
import kotlin.reflect.typeOf

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
    private val url = "https://www.google.co.ke"
    private val urlToImage = "https://www.google.co.ke/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"
    private val publishedAt = "2019-08-08T08:00:00Z"
    private val content = "content"

    private val article = TestUtils.createArticle()
    private val newsObject = TestUtils.createNewsItem()
    private  val networkArticle = NetworkArticle(source, author,title, description, url, urlToImage, publishedAt, content)

    private val networkContainer = NetworkArticleContainer(listOf(networkArticle, networkArticle))
    private val  databaseEverything = networkContainer.asDatabaseEverytingModel()
    private val  databaseTopHeadlines = networkContainer.asDatabaseTopHeadlinesModel()

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

    @Test
    fun testNetworkArticle() {
        assertEquals(source, networkArticle.source)
        assertEquals(author, networkArticle.author)
        assertEquals(title, networkArticle.title)
        assertEquals(description, networkArticle.description)
        assertEquals(url, networkArticle.url)
        assertEquals(urlToImage, networkArticle.urlToImage)
        assertEquals(publishedAt, networkArticle.publishedAt)
        assertEquals(content, networkArticle.content)
    }

    @Test
    fun testMapToDatabaseEverything() {
        assertEquals(2, databaseEverything.size)
        assertEquals(
            DatabaseEverything(source, author,title, description, url, urlToImage, publishedAt, content), databaseEverything[1]
        )

        assertEquals(source, databaseEverything[0].source)
        assertEquals(author, databaseEverything[0].author)
        assertEquals(title, databaseEverything[0].title)
        assertEquals(description, databaseEverything[0].description)
        assertEquals(url, databaseEverything[0].url)
        assertEquals(urlToImage, databaseEverything[0].urlToImage)
        assertEquals(publishedAt, databaseEverything[0].publishedAt)
        assertEquals(content, databaseEverything[0].content)
    }

    @Test
    fun testMapToDatabaseTopHeadline() {
        assertEquals(2, databaseEverything.size)
        assertEquals(
            DatabaseTopHeadlines(source, author,title, description, url, urlToImage, publishedAt, content), databaseTopHeadlines[1]
        )

        assertEquals(source, databaseTopHeadlines[0].source)
        assertEquals(author, databaseTopHeadlines[0].author)
        assertEquals(title, databaseTopHeadlines[0].title)
        assertEquals(description, databaseTopHeadlines[0].description)
        assertEquals(url, databaseTopHeadlines[0].url)
        assertEquals(urlToImage, databaseTopHeadlines[0].urlToImage)
        assertEquals(publishedAt, databaseTopHeadlines[0].publishedAt)
        assertEquals(content, databaseTopHeadlines[0].content)
    }

    @Test
    fun testMapToEverythingModel() {
        val databaseEverything = databaseEverything.toList()
        val everything = databaseEverything.asEverythingDomainModel()

        assertEquals(2, everything.size)
        assertEquals(source, everything[0].source)
        assertEquals(author, everything[0].author)
        assertEquals(title, everything[0].title)
        assertEquals(description, everything[0].description)
        assertEquals(url, everything[0].url)
        assertEquals(urlToImage, everything[0].urlToImage)
        assertEquals(publishedAt, everything[0].publishedAt)
        assertEquals(content, everything[0].content)

    }

    @Test
    fun testMapToTopHeadlinesModel() {
        val databaseTopHeadlines = databaseTopHeadlines.toList()
        val topHeadlines = databaseTopHeadlines.asTopHeadlinesDomainModel()

        assertEquals(2, topHeadlines.size)
        assertEquals(source, topHeadlines[0].source)
        assertEquals(author, topHeadlines[0].author)
        assertEquals(title, topHeadlines[0].title)
        assertEquals(description, topHeadlines[0].description)
        assertEquals(url, topHeadlines[0].url)
        assertEquals(urlToImage, topHeadlines[0].urlToImage)
        assertEquals(publishedAt, topHeadlines[0].publishedAt)
        assertEquals(content, topHeadlines[0].content)

    }

    @Test
    fun testTypeConverter() {
        val sourceName = RoomTypeConverters.sourceToString(source)

        sourceName?.isNotBlank()?.let { assertTrue(it) }
        assertEquals("name", sourceName)

        val newSource = RoomTypeConverters.stringToSource(sourceName)
        assertEquals(newSource?.name, source.name)
    }

    @Test
    fun testDateUtil() {
        val simpleDate = DateUtils.convertToSimpleDateFormat(publishedAt)

        assertEquals("2019-08-08", simpleDate)
    }

    @Mock
    val fragment = Fragment()

    @Test
    fun test_FragmentViewExtensions() {
        val networkConnected = fragment.isNetworkConnected()

        assertTrue(!networkConnected)

        fragment.view?.let { fragment.showErrorSnackBar(it, "Error" ) }
    }
}