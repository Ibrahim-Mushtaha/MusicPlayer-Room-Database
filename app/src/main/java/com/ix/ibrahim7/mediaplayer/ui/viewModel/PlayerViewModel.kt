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
import com.ix.ibrahim7.mediaplayer.model.MusicFile
import com.ix.ibrahim7.mediaplayer.model.SongModel
import com.ix.ibrahim7.mediaplayer.repository.SongRepository
import com.ix.ibrahim7.mediaplayer.util.Constant
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.coroutines.*
import java.lang.Exception


class PlayerViewModel(application: Application) : AndroidViewModel(application) {


    var play = true

    val dataPlayLiveData = MutableLiveData<Int>()
    val dataPositionLiveData = MutableLiveData<Int>()
    val dataMediaPlayerLiveData = MutableLiveData<MediaPlayer>()

    val repository= SongRepository(application)

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    lateinit var mediaPlayer: MediaPlayer





    fun addToCurrentPlay(arrayList: MutableList<SongModel>, position: Int)= uiScope.launch {
        repository.insertSong(arrayList[position])
    }


    fun startSong(arrayList: MutableList<SongModel>, position: Int) {
        uiScope.launch {
            try {
                mediaPlayer = MediaPlayer()
                mediaPlayer.setDataSource(arrayList[position].data);
                mediaPlayer.prepare();
                mediaPlayer.start();
                dataMediaPlayerLiveData.postValue(mediaPlayer)
                dataPositionLiveData.postValue(position)
                repository.insertSong(arrayList[position])
            }catch (e: Exception){
                Log.e("eee catch",e.message.toString())
            }
        }
    }

    fun StopSong(){
        uiScope.launch {
            if (play) {
                mediaPlayer.pause()
                dataPlayLiveData.postValue(Constant.STOP)
                play = false
            } else {
                dataPlayLiveData.postValue(Constant.PLAY)
                mediaPlayer.start()
                play = true
            }
        }
    }


    fun NextPreviousSong(arrayList: MutableList<SongModel>, position: Int){
        uiScope.launch {
            mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(arrayList[position].data);
            mediaPlayer.prepare();
            mediaPlayer.start();
            dataPositionLiveData.postValue(position)
            dataMediaPlayerLiveData.postValue(mediaPlayer)
            repository.insertSong(arrayList[position])
        }
    }




}

