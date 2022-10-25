package com.a5bar.response


import com.a5bar.model.NewsModel
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("articles")
    val newsModel: MutableList<NewsModel>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)