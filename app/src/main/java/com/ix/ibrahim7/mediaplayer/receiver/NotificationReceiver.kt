package com.ix.ibrahim7.mediaplayer.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.ix.ibrahim7.mediaplayer.model.SongModel
import com.ix.ibrahim7.mediaplayer.service.MusicService
import com.ix.ibrahim7.mediaplayer.util.Constant.ACTION_NAME
import com.ix.ibrahim7.mediaplayer.util.Constant.ACTION_NEXT
import com.ix.ibrahim7.mediaplayer.util.Constant.ACTION_PLAY
import com.ix.ibrahim7.mediaplayer.util.Constant.ACTION_PREVIOUS
import com.ix.ibrahim7.mediaplayer.util.Constant.ARRAY
import com.ix.ibrahim7.mediaplayer.util.Constant.POSITION

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val actionName = intent?.action
        if (actionName != null) {
            val serviceIntent = Intent(context, MusicService::class.java)
            val array=intent.getParcelableArrayListExtra<SongModel>(ARRAY)
            var position=intent.getIntExtra(POSITION,0)
            when (actionName) {
                ACTION_PLAY -> {
                    serviceIntent.apply {
                        putExtra(ACTION_NAME, ACTION_PLAY)
                        putParcelableArrayListExtra(ARRAY,array)
                        putExtra(POSITION,position)
                    }
                    context!!.startService(serviceIntent)
                    Toast.makeText(context,"ACTION_PLAY",Toast.LENGTH_SHORT).show()
                }
                ACTION_NEXT -> {
                    position=((position + 1) % array!!.size)
                    serviceIntent.apply {
                        putExtra(ACTION_NAME, ACTION_NEXT)
                        putParcelableArrayListExtra(ARRAY,array)
                        putExtra(POSITION, position)
                    }
                    context!!.startService(serviceIntent)
                }
                ACTION_PREVIOUS -> {
                    position=if ((position - 1) < 0) (array!!.size - 1) else (position - 1)
                    serviceIntent.apply {
                        putExtra(ACTION_NAME, ACTION_PREVIOUS)
                        putParcelableArrayListExtra(ARRAY,array)
                        putExtra(POSITION,position)
                    }
                    context!!.startService(serviceIntent)
                }
            }
        }
    }

}