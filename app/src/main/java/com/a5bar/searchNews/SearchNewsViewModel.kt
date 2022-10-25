package com.a5bar.searchNews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a5bar.repository.SearchNewsRepository
import com.a5bar.response.NewsResponse
import com.a5bar.common.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchNewsViewModel(
    private val searchNewsRepository: SearchNewsRepository
) : ViewModel() {

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse ?= null



    fun searchNews(searchQuery: String) {
        viewModelScope.launch {
            searchNews.postValue(Resource.Loading())
            val response = searchNewsRepository.searchNews(searchQuery, searchNewsPage)
            searchNews.postValue(handleSearchNewsResponse(response))
        }
    }


    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
//                searchNewsPage++
//                if (searchNewsResponse == null) {
//                    searchNewsResponse = it
//                } else {
//                    val oldArticles = searchNewsResponse?.newsModel
//                    val newArticles = it.newsModel
//                    oldArticles?.addAll(newArticles)
//                }
                return Resource.Success(it)
//                return Resource.Success(searchNewsResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }
}