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

//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as NewsModel
//
//        if (id != other.id) return false
//        if (author != other.author) return false
//        if (content != other.content) return false
//        if (description != other.description) return false
//        if (publishedAt != other.publishedAt) return false
//        if (source != other.source) return false
//        if (title != other.title) return false
//        if (url != other.url) return false
//        if (urlToImage != other.urlToImage) return false
//
//        return true
//    }

}