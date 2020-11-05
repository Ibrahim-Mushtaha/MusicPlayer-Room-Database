package com.ix.ibrahim7.mediaplayer.ui.viewModel

import android.app.Application
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ix.ibrahim7.mediaplayer.loader.SongDataLab
import com.ix.ibrahim7.mediaplayer.model.AlbumFile
import com.ix.ibrahim7.mediaplayer.model.AlbumModel
import kotlinx.coroutines.*

class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val _AlbumLiveData = MutableLiveData<ArrayList<AlbumModel>>()

    val AlbumliveData: LiveData<ArrayList<AlbumModel>>
        get() = _AlbumLiveData

    val contect =application
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    init {
        uiScope.launch {
            getAlbums(application)
        }
    }

    fun getAlbums(context: Context) {
        return _AlbumLiveData.postValue(SongDataLab.get(context).albums)
    }





}

