package com.pemwa.topnews

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pemwa.topnews.database.ArticlesDao
import com.pemwa.topnews.database.ArticlesDatabase
import com.pemwa.topnews.database.asEverythingDomainModel
import com.pemwa.topnews.database.asTopHeadlinesDomainModel
import com.pemwa.topnews.util.TestUtils
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE)
class RoomDatabaseTest {

    private lateinit var articlesDao: ArticlesDao
    private lateinit var articlesDatabase: ArticlesDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        articlesDatabase = Room.inMemoryDatabaseBuilder(
            context, ArticlesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        articlesDao = articlesDatabase.articlesDao
    }

    @After
    fun closeDb() {
        articlesDatabase.close()
    }

    @Test
    fun test_InsertArticle_andReadInList() {
        val everything = TestUtils.createArticleDatabaseEverything()
        val topHeadlines = TestUtils.createArticleDatabaseTopHeadlines()

        articlesDao.insertIntoEverything(everything)

        val returnedEverything = articlesDao.getFromEverything().value?.asEverythingDomainModel()
        assertEquals(returnedEverything?.get(0), null)

        articlesDao.insertIntoTopHeadlines(topHeadlines)

        val returnedTopHeadlines = articlesDao.getFromTopHeadlines().value?.asTopHeadlinesDomainModel()
        assertEquals(returnedTopHeadlines?.get(0), null)
    }
}