package com.ix.ibrahim7.mediaplayer.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ix.ibrahim7.mediaplayer.model.FavoriteModel
import com.ix.ibrahim7.mediaplayer.model.SongModel

@Dao
interface SongDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(song: SongModel)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertToFavorite(song: FavoriteModel)


    @Update
    fun update(song: SongModel)



    @Query("SELECT * FROM task_table")
    fun getAllSong(): LiveData<List<SongModel>>


    @Query("SELECT * FROM favorite_table")
    fun getAllFavorite(): LiveData<List<FavoriteModel>>



    @Query("SELECT * FROM task_table where id > :name")
    fun getSong(name:String): LiveData<List<SongModel>>?

    @Query("SELECT * FROM task_table where id > :id")
    fun getFavorite(id:String): LiveData<FavoriteModel>?

    @Delete
    fun delete(song: SongModel)

    @Delete
    fun delete(song: FavoriteModel)



}
