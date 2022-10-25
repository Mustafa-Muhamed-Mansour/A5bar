package com.a5bar.savedNews

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a5bar.model.NewsModel
import com.a5bar.repository.BreakingNewsRepository
import kotlinx.coroutines.launch

class SavedNewsViewModel(
    private val breakingNewsRepository: BreakingNewsRepository
) : ViewModel() {



    fun saveArticles(newsModel: NewsModel){
        viewModelScope.launch {
            breakingNewsRepository.insertArticle(newsModel)
        }
    }

    fun getSaveArticles(): LiveData<List<NewsModel>> {
        return breakingNewsRepository.getSavedNews()
    }

    fun deleteArticles(newsModel: NewsModel){
        viewModelScope.launch {
            breakingNewsRepository.deleteArticle(newsModel)
        }
    }

}