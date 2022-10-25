package com.a5bar.repository

import androidx.lifecycle.LiveData
import com.a5bar.model.NewsModel
import com.a5bar.network.local.NewsDB
import com.a5bar.network.remote.NewsClient
import com.a5bar.response.NewsResponse
import retrofit2.Response

class BreakingNewsRepository(
    private val db: NewsDB
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse> {
        return NewsClient.getApiNew.getBreakingNews(countryCode, pageNumber)
    }

    suspend fun insertArticle(newsModel: NewsModel){
        db.getNewDao().insertArticle(newsModel)
    }

    fun getSavedNews(): LiveData<List<NewsModel>>{
        return db.getNewDao().getAllArticles()
    }

    suspend fun deleteArticle(newsModel: NewsModel){
        db.getNewDao().deleteArticle(newsModel)
    }
}