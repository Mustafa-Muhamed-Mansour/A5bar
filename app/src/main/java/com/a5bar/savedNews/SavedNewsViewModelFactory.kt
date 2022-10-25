package com.a5bar.savedNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a5bar.repository.BreakingNewsRepository

class SavedNewsViewModelFactory(
    private val breakingNewsRepository: BreakingNewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        return SavedNewsViewModel(breakingNewsRepository) as T
    }

}