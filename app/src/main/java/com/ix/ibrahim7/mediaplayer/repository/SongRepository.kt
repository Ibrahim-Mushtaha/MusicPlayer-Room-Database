package com.ix.ibrahim7.mediaplayer.repository

import android.app.Application
import com.ix.ibrahim7.mediaplayer.database.SongDao
import com.ix.ibrahim7.mediaplayer.database.SongDatabase
import com.ix.ibrahim7.mediaplayer.model.FavoriteModel
import com.ix.ibrahim7.mediaplayer.model.SongModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SongRepository(application: Application)  {


    var data: SongDatabase?=null
    var mDao: SongDao

    init {
        data = SongDatabase.getInstance(application)
        mDao =data!!.taskDao
    }


    fun getAllSong() = mDao.getAllSong()
    fun getAllFavorite() = mDao.getAllFavorite()
    fun getSearchTask(name:String) = mDao.getSong(name)


     suspend fun insertSong(song: SongModel) {
        withContext(Dispatchers.IO){
            mDao.insert(song)
        }
    }


    suspend fun addToFavorite(song: SongModel) {
        withContext(Dispatchers.IO){
            mDao.insertToFavorite(FavoriteModel(song.id,song.title,song.artistName,song.composer,song.albumName,song.albumArt,song.data,song.trackNumber
            ,song.year,song.duration,song.dateModified,song.dateModified,song.albumId,song.artistId,song.bookmark))
        }
    }


    suspend fun deleteSong(song: SongModel){
        withContext(Dispatchers.IO){
            mDao.delete(song)
        }
    }

    suspend fun deleteFavorite(song: FavoriteModel){
        withContext(Dispatchers.IO){
            mDao.delete(song)
        }
    }




}