package com.ix.ibrahim7.mediaplayer.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import com.ix.ibrahim7.mediaplayer.model.SongModel
import com.ix.ibrahim7.mediaplayer.util.Constant
import java.lang.Exception

class MusicService : Service(), MediaPlayer.OnCompletionListener{

 lateinit var myNotificationManager: MyNotificationManager
  var mediaPlayer: MediaPlayer?=null
 val mBinder: IBinder = MyBinder()
 var position=-1

 var uri: Uri? = null
 var actionPlaying: ActionPlaying? = null
 var musicFiles: List<SongModel> = ArrayList<SongModel>()
 var musicFiles2= ArrayList<SongModel>()


 override fun onBind(intent: Intent?): IBinder? {
  return mBinder
 }

 override fun onCreate() {
  super.onCreate()
 }


 inner class MyBinder : Binder() {
  fun getService() = this@MusicService
 }

 override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
  try {
   position= intent!!.getIntExtra(Constant.POSITION,0)


   musicFiles2 = intent.getParcelableArrayListExtra<SongModel>(Constant.ARRAY)!!

   val actionName = intent.getStringExtra("ActionName")
   if (position != -1 && intent.hasExtra(Constant.POSITION))
//   musicFiles2=array

   playMedia(position)



   if (actionName != null) {
    when (actionName) {
     Constant.ACTION_PLAY -> {
      Toast.makeText(this, "playPause", Toast.LENGTH_LONG).show()
      if (actionPlaying != null) {
       actionPlaying!!.playPauseBtnClicked()
      }
     }
     Constant.ACTION_NEXT -> {
      Toast.makeText(this, "next", Toast.LENGTH_LONG).show()
      if (actionPlaying != null) {
       actionPlaying!!.nextBtnClicked()
      }
     }
     Constant.ACTION_PREVIOUS -> {
      Toast.makeText(this, "previous", Toast.LENGTH_LONG).show()
      if (actionPlaying != null) {
       actionPlaying!!.prevBtnClicked()
      }
     }
    }
   }
  }catch (e: Exception){
   Log.e("eee catch service",e.message.toString())
  }
  return START_STICKY
 }



 private fun playMedia(startPosition: Int) {

  position = startPosition
  if (mediaPlayer != null) {
   if (mediaPlayer!!.isPlaying) {
    mediaPlayer!!.stop()
    mediaPlayer!!.release()
   }

   createMediaPlayer(position)
   mediaPlayer!!.start()
  } else {
   createMediaPlayer(position)
   mediaPlayer!!.start()
  }

 }


 fun start() {
  mediaPlayer!!.start()
 }

 fun pause() {
  mediaPlayer!!.pause()
 }

 fun isPlaying(): Boolean {
  return mediaPlayer!!.isPlaying
 }

 fun stop() {
  mediaPlayer!!.stop()
 }

 fun release() {
  mediaPlayer!!.release()
 }

 fun getDuration(): Int {
  return mediaPlayer!!.duration
 }

 fun seekTo(position: Int) {
  mediaPlayer!!.seekTo(position)
 }

 fun getCurrentPosition(): Int {
  return mediaPlayer!!.currentPosition
 }

 fun createMediaPlayer(position: Int) {
  try {
   uri = musicFiles2[position].data!!.toUri()
   mediaPlayer = MediaPlayer.create(baseContext, uri)
   onCompleted()
  }catch (e:Exception){
   Log.e("EEee",e.message.toString())
  }
 }

 fun onCompleted() {
  mediaPlayer!!.setOnCompletionListener(this)
 }

 override fun onCompletion(mp: MediaPlayer?) {
  if (actionPlaying != null) {
   actionPlaying!!.nextBtnClicked()
  }
 createMediaPlayer(position)
  mediaPlayer!!.start()
  onCompleted()
 }

 fun setCallback(actionPlaying: ActionPlaying) {
  this.actionPlaying = actionPlaying
 }


}