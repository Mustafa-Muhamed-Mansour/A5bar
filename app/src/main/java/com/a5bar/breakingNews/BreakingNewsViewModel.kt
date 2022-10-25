package com.a5bar.breakingNews

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Email.TYPE_MOBILE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a5bar.model.NewsModel
import com.a5bar.repository.BreakingNewsRepository
import com.a5bar.response.NewsResponse
import com.a5bar.ui.NewsApplication
import com.a5bar.common.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class BreakingNewsViewModel(
    private val app: Application,
    private val breakingNewsRepository: BreakingNewsRepository
) : AndroidViewModel(app) {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null


    init {
        getBreakingNews("")
    }

    fun getBreakingNews(countryCode: String) {
        viewModelScope.launch {
//            breakingNews.postValue(Resource.Loading())
//            val response = breakingNewsRepository.getBreakingNews(countryCode, breakingNewsPage)
//            breakingNews.postValue(handleBreakingNewsResponse(response))
            safeBreakingNewsCall(countryCode)
        }
    }
//        viewModelScope.launch {
//            safeBreakingNewsCall(countryCode)
//        }
//        viewModelScope.launch {
//            breakingNews.postValue(Resource.Loading())
//            val response = breakingNewsRepository.getBreakingNews(countryCode, breakingNewsPage)
//            breakingNews.postValue(handleBreakingNewsResponse(response))
//        }
//    }
//    = viewModelScope.launch {
//        breakingNews.postValue(Resource.Loading())
//        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
//        breakingNews.postValue(handleBreakingNewsResponse(response))
//        safeBreakingNewsCall(countryCode)
//    }


    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
//                breakingNewsPage++
//                if (breakingNewsResponse == null) {
//                    breakingNewsResponse = it
//                } else {
//                    val oldArticles = breakingNewsResponse?.newsModel
//                    val newArticles = it.newsModel
//                    oldArticles?.addAll(newArticles)
//                }

                return Resource.Success(it)
//                return Resource.Success(breakingNewsResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }


    private suspend fun safeBreakingNewsCall(countryCode: String) {
        breakingNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = breakingNewsRepository.getBreakingNews(countryCode, breakingNewsPage)
                breakingNews.postValue(handleBreakingNewsResponse(response))
            } else {
                breakingNews.postValue(Resource.Error("No Internet Connection"))
            }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> breakingNews.postValue(Resource.Error("Network failure"))
                else -> breakingNews.postValue(Resource.Error("Conversation error"))
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

    fun insertArticle(newsModel: NewsModel) {
        viewModelScope.launch {
            breakingNewsRepository.insertArticle(newsModel)
        }
    }
}