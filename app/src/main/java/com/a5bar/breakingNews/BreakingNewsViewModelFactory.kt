package com.a5bar.breakingNews

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a5bar.repository.BreakingNewsRepository

class BreakingNewsViewModelFactory (
    val app: Application,
    private val breakingNewsRepository: BreakingNewsRepository
): ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        return BreakingNewsViewModel(app, breakingNewsRepository) as T
    }
}