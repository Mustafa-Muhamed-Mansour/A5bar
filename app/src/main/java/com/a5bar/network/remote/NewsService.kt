package com.a5bar.network.remote

import com.a5bar.common.Constant
import com.a5bar.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String, // = "eg"
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = Constant.API_KEY_ONE
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = Constant.API_KEY_ONE
    ): Response<NewsResponse>
}