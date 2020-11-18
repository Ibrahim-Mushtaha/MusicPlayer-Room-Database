package com.ix.ibrahim7.mediaplayer.ui.viewModel

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.model.FavoriteModel
import com.ix.ibrahim7.mediaplayer.model.MusicFile
import com.ix.ibrahim7.mediaplayer.model.SongModel
import com.ix.ibrahim7.mediaplayer.repository.SongRepository
import com.ix.ibrahim7.mediaplayer.util.Constant
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.coroutines.*
import java.lang.Exception


class AllListViewModel(application: Application) : AndroidViewModel(application) {



    val repository= SongRepository(application)

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun getdate() =repository.getAllSong()


    fun deleteSong(songModel: SongModel) =uiScope.launch {
        repository.deleteSong(songModel)
    }

    fun deleteFavorite(song: SongModel) =uiScope.launch {
        repository.deleteFavorite(FavoriteModel(song.id,song.title,song.artistName,song.composer,song.albumName,song.albumArt,song.data,song.trackNumber
            ,song.year,song.duration,song.dateModified,song.dateModified,song.albumId,song.artistId,song.bookmark))
    }

    fun getFavorite() = repository.getAllFavorite()


}

