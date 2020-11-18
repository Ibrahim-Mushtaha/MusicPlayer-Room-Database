package com.ix.ibrahim7.mediaplayer.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ix.ibrahim7.mediaplayer.model.FavoriteModel
import com.ix.ibrahim7.mediaplayer.model.SongModel

@Database(entities = [SongModel::class,FavoriteModel::class], version = 2,exportSchema = false)
abstract class SongDatabase : RoomDatabase() {

    abstract val taskDao : SongDao

    companion object{

        private var INSTANCE: SongDatabase? = null

        fun getInstance(activity: Application): SongDatabase {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        activity,
                        SongDatabase::class.java,
                        "song_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        fun CreateBuilder(context: Context) = Room.databaseBuilder(context.applicationContext,
            SongDatabase::class.java,"song.db").build()
    }







}