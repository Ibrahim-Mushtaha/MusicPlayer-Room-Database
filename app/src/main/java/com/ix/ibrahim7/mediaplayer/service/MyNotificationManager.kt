package com.ix.ibrahim7.mediaplayer.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.model.SongModel
import com.ix.ibrahim7.mediaplayer.receiver.NotificationReceiver
import com.ix.ibrahim7.mediaplayer.ui.activity.BasicActivity
import com.ix.ibrahim7.mediaplayer.util.Constant
import com.ix.ibrahim7.mediaplayer.util.Constant.ACTION_NEXT
import com.ix.ibrahim7.mediaplayer.util.Constant.ACTION_PLAY
import com.ix.ibrahim7.mediaplayer.util.Constant.ACTION_PREVIOUS
import com.ix.ibrahim7.mediaplayer.util.Constant.ARRAY
import com.ix.ibrahim7.mediaplayer.util.Constant.POSITION
import com.ix.ibrahim7.mediaplayer.util.Constant.getAlbumArt

class MyNotificationManager(var context: Context) {
    private var mediaSessionCompat: MediaSessionCompat? = null

    companion object {
        val CHANNEL_ID = "2000"
    }


    fun showNotification(context: Context, playPauseBtn: Int, arrayList: ArrayList<SongModel>, position: Int, musicService: MusicService?
                         ,mediaSessionCompat: MediaSessionCompat,playing:Boolean) {
        val intent = Intent(context, BasicActivity::class.java)

        val contentIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val prevIntent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra(POSITION, position)
            putParcelableArrayListExtra(
                ARRAY,
               arrayList
            )
        }
        Log.e("eee x",arrayList.toString())
        prevIntent.action = ACTION_PREVIOUS
        val contentPrev = PendingIntent
            .getBroadcast(context, 0, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT)


        val nextIntent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra(POSITION, position)
            putParcelableArrayListExtra(
                ARRAY,
                arrayList
            )
        }

        nextIntent.action = ACTION_NEXT
        val contentNext = PendingIntent
            .getBroadcast(context, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT)


        val playIntent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra(POSITION, position)
            putParcelableArrayListExtra(
                ARRAY,
                arrayList.toMutableList() as ArrayList<SongModel>
            )
        }
        playIntent.action = ACTION_PLAY
        val contentPlay = PendingIntent
            .getBroadcast(context, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT)


        val picture = getAlbumArt(
            arrayList[position].data!!
        )
        var thump: Bitmap? = null
        if (picture != null)
            thump = BitmapFactory
                .decodeByteArray(picture, 0, picture.size)
        else
            thump = BitmapFactory.decodeResource(context.resources, R.drawable.ic_album_cover)

        val style = androidx.media.app.NotificationCompat.MediaStyle()
        val nBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
        val notification = nBuilder
            .setSmallIcon(playPauseBtn)
            .setLargeIcon(thump)
            .setContentIntent(contentIntent)
            .setContentTitle(arrayList[position].title)
            .setContentText(arrayList[position].artistName)
            .addAction(R.drawable.ic_arrow_back, Constant.ACTION_PREVIOUS, contentPrev)
            .addAction(if (playing){R.drawable.ic_pause}else R.drawable.ic_arrow_play, Constant.ACTION_PLAY, contentPlay)
            .addAction(R.drawable.ic_arrow_next, Constant.ACTION_NEXT, contentNext)
            .setSound(null)
            .setStyle(
                style.setMediaSession(mediaSessionCompat!!.sessionToken)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOnlyAlertOnce(true)

        val notificationManager =
            context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "first channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.setShowBadge(true)
            channel.enableVibration(false)
            channel.enableLights(true)
            notificationManager.createNotificationChannel(channel)
        }

        musicService!!.startForeground(1, notification.build())
    }


}