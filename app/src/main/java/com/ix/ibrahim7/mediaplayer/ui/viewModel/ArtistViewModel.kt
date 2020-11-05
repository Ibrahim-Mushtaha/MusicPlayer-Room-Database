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
import com.ix.ibrahim7.mediaplayer.model.ArtistModel
import kotlinx.coroutines.*

class ArtistViewModel(application: Application) : AndroidViewModel(application) {

    private val _ArtistLiveData = MutableLiveData<ArrayList<ArtistModel>>()

    val ArtistliveData: LiveData<ArrayList<ArtistModel>>
        get() = _ArtistLiveData

    val contect =application
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    init {
        uiScope.launch {
            getArtist(application)
        }
    }

    fun getArtist(context: Context) {
        return _ArtistLiveData.postValue(SongDataLab.get(context).artists)
    }





}

