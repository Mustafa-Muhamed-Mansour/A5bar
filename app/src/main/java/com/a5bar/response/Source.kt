package com.a5bar.response


import com.google.gson.annotations.SerializedName


data class Source(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)