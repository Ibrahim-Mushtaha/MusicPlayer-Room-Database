package com.ix.ibrahim7.mediaplayer.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ix.ibrahim7.mediaplayer.loader.SongDataLab
import com.ix.ibrahim7.mediaplayer.model.SongModel
import com.ix.ibrahim7.mediaplayer.repository.SongRepository
import kotlinx.coroutines.*

class SongViewModel(application: Application) : AndroidViewModel(application) {


    private val _SongModelLiveData = MutableLiveData<ArrayList<SongModel>>()

    val SongModelliveData: LiveData<ArrayList<SongModel>>
        get() = _SongModelLiveData

    val repository= SongRepository(application)

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)



    init {
        uiScope.launch {
            getSongs(application)
        }
    }


    fun getSongs(application: Application) {
        return _SongModelLiveData.postValue(SongDataLab.get(application).songs as ArrayList<SongModel>?)
    }


    fun addTofavorite(songModel: SongModel){
        uiScope.launch {
            repository.addToFavorite(songModel)
        }
    }

}

