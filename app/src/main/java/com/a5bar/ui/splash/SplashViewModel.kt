package com.a5bar.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val booleanMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun delayTime(): LiveData<Boolean> {
        viewModelScope.launch {
            delay(3000)
            booleanMutableLiveData.postValue(true)
        }
        return booleanMutableLiveData
    }

}