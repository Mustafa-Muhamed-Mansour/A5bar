package com.a5bar.network.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.a5bar.model.NewsModel


@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(newsModel: NewsModel): Long


    @Query("select * from table_articles_database")
    fun getAllArticles(): LiveData<List<NewsModel>>


    @Delete
    suspend fun deleteArticle(newsModel: NewsModel)
}