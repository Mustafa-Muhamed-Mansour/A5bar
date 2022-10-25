package com.a5bar.searchNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a5bar.repository.SearchNewsRepository

class SearchNewViewModelFactory(
    private val searchNewsRepository: SearchNewsRepository
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        return SearchNewsViewModel(searchNewsRepository) as T
    }


}