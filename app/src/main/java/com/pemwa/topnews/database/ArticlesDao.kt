package com.pemwa.topnews.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * A Dao Interface for the articles database
 */
@Dao
interface ArticlesDao {

    /**
     * A mapping function to update all the articles in the [articles_everything] database table
     * @param articles an array of [DatabaseEverything] objects fetched from the network
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIntoEverything(vararg articles: DatabaseEverything)

    /**
     * A mapping function to get all the articles from the [articles_everything] database table
     * @return LiveData<List<DatabaseEverything>> returns a list of the [DatabaseEverything] objects as LiveData
     */
    @Query("SELECT * FROM articles_everything")
    fun getFromEverything() : LiveData<List<DatabaseEverything>>

    /**
     * A mapping function to update all the articles in the [articles_top_headlines] database table
     * @param articles an array of [DatabaseTopHeadlines] objects fetched from the network
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIntoTopHeadlines(vararg articles: DatabaseTopHeadlines)

    /**
     * A mapping function to get all the articles from the [articles_top_headlines] database table
     * @return LiveData<List<DatabaseTopHeadlines>> returns a list of the [DatabaseTopHeadlines] objects as LiveData
     */
    @Query("SELECT * FROM articles_top_headlines")
    fun getFromTopHeadlines() : LiveData<List<DatabaseTopHeadlines>>
}
