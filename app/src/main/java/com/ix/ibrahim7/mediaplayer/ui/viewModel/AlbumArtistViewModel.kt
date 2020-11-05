package com.ix.ibrahim7.mediaplayer.ui.viewModel

import android.app.Application
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.*
import com.ix.ibrahim7.mediaplayer.loader.SongDataLab
import com.ix.ibrahim7.mediaplayer.model.AlbumFile
import com.ix.ibrahim7.mediaplayer.model.ArtistModel
import kotlinx.coroutines.*

class AlbumArtistViewModel(application: Application) : AndroidViewModel(application) {

    private val _SongLiveData = MutableLiveData<ArrayList<AlbumFile>>()

    val SongliveData: LiveData<ArrayList<AlbumFile>>
        get() = _SongLiveData



    fun getData(context: Context,id: String):LiveData<ArrayList<AlbumFile>>{
        val array = ArrayList<AlbumFile>()

        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val x = arrayOf(
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ARTIST
        )


        val cursor = context.contentResolver.query(uri, x, "_id=?", arrayOf<String>(id), null)

        if (cursor != null) {
            var i = 0
            while (cursor.moveToNext()) {
                val album = cursor.getString(0)
                val artists = cursor.getString(1)
                val number = cursor.getString(2)
                val art = cursor.getString(3)
                val album_id = cursor.getString(4)
                val album_song = cursor.getString(5)

                  Log.e("eee album number","{$album},{$artists},{$number},{$art},{$album_id},{$album_song}")
                if (art == null){
                    array.add(AlbumFile(album,artists,number,null))
                }else {
                    array.add(AlbumFile(album, artists, number, art))
                }
            }
            cursor.close()
            _SongLiveData.postValue(array)
            //Log.e("eee art",art.toString())
        }

        return SongliveData
    }

}

