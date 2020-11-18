package com.ix.ibrahim7.mediaplayer.ui.viewModel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    val liveData: LiveData<SplashState>
        get() = mutableLiveData

    private val mutableLiveData = MutableLiveData<SplashState>()

    init {
        GlobalScope.launch {
            delay(2260)
            mutableLiveData.postValue(SplashState.MainActivity)
        }
    }



}

sealed class SplashState {
    object MainActivity : SplashState()
}
