package com.a5bar.repository

import com.a5bar.network.remote.NewsClient
import com.a5bar.response.NewsResponse
import retrofit2.Response

class SearchNewsRepository {

    suspend fun searchNews(searchQuery: String, pageNumber: Int): Response<NewsResponse> {
        return NewsClient.getApiNew.searchForNews(searchQuery, pageNumber)
    }
}