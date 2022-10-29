package com.a5bar.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.a5bar.common.Constant
import com.a5bar.response.Source
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(
    tableName = Constant.TABLE_NAME
)

data class NewsModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int ?= null,
    @SerializedName("author")
    val author: String ?= null,
    @SerializedName("content")
    val content: String ?= null,
    @SerializedName("description")
    val description: String ?= null,
    @SerializedName("publishedAt")
    val publishedAt: String ?= null,
    @SerializedName("source")
    val source: Source ?= null,
    @SerializedName("title")
    val title: String ?= null,
    @SerializedName("url")
    val url: String ?= null,
    @SerializedName("urlToImage")
    val urlToImage: String ?= null
) : Serializable
{

    override fun hashCode(): Int {
        var result = id.hashCode()
        if (url.isNullOrEmpty()) {
            result = 31 * result + url.hashCode()
        }
        return result
    }
}