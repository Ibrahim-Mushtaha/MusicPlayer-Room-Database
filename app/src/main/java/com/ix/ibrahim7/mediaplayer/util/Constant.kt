package com.ix.ibrahim7.mediaplayer.util

import android.media.MediaMetadataRetriever
import android.util.Log
import java.lang.Exception

object Constant {

    val PLAY = 1
    val STOP = 0
    fun getAlbumArt(uri: String): ByteArray? {
        lateinit var art :ByteArray
        try {
            val retriver = MediaMetadataRetriever()
            retriver.setDataSource(uri)
            art = retriver.embeddedPicture!!
            retriver.release()
        }catch (e: Exception){
            art= ByteArray(0)
            Log.e("Eeee image error encode",e.message.toString())
        }
        return art
    }


}