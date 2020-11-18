package com.ix.ibrahim7.mediaplayer.util

import android.app.Activity
import android.app.Dialog
import android.content.ContentUris
import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.model.SongModel
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_player.*
import java.io.File
import java.lang.Exception

object Constant {

    val PLAY = 1
    val STOP = 0
    val TAG = "eee"

    const val ACTION_PREVIOUS = "actionPrevious"
    const val ACTION_NEXT = "actionNext"
    const val ACTION_PLAY = "actionPlay"
    const val ARRAY = "array"
    const val POSITION = "position"
    const val ACTION_NAME = "ActionName"
    const val TYPE = "type"
    const val SORTTYPE = "sort_type"


    fun getSharePref(context: Context) =
        context.getSharedPreferences("Share", Activity.MODE_PRIVATE)

    fun editor(context: Context) = getSharePref(context).edit()

    lateinit var dialog: Dialog
    fun showDialog(activity: Activity) {
        dialog = Dialog(activity).apply {
            setContentView(R.layout.dialog_loading)
            setCancelable(false)
        }
        dialog.show()
    }



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


    fun Move(navController: NavController,navDirections: NavDirections){
        navController.navigate(navDirections)
    }


    fun pattleImage(context: Context,arrayList: MutableList<SongModel>,position:Int,onComplete:(swatch:Palette.Swatch?)->Unit){
        val bitmap = BitmapFactory.decodeByteArray(
            getAlbumArt(arrayList[position].data),
            0,
            getAlbumArt(arrayList[position].data)!!.size
        )

        if (bitmap != null) {
            Palette.from(bitmap).generate { palette ->
                val swatch = palette!!.dominantSwatch
                if (swatch != null) {
                    onComplete(swatch)
                }
            }
        }else{
            val bitmap =BitmapFactory.decodeResource(context.resources, R.drawable.ic_image)
            Palette.from(bitmap).generate { palette ->
                val swatch = palette!!.dominantSwatch
                if (swatch != null) {
                    onComplete(swatch)
                }
            }
        }
    }



    fun millisecondsToTime(milliseconds: Int): CharSequence {
        val minutes = milliseconds / 1000 / 60
        val seconds = milliseconds / 1000 % 60
        val secondsStr = seconds.toString()
        val secs: String
        secs = if (secondsStr.length >= 2) {
            secondsStr.substring(0, 2)
        } else {
            "0$secondsStr"
        }
        Log.e("secs", "${secs}")
        return "$minutes:$secs"
    }



    fun getImage(context: Context,arrayList: MutableList<SongModel>,position:Int,imageView: ImageView){
        Glide.with(context).asBitmap()
            .load(arrayList[position].albumArt)
            .error(R.drawable.ic_album_cover)
            .into(imageView)
    }

    fun getBlurImage(context: Context,arrayList: MutableList<SongModel>,position:Int,imageView: ImageView){
        Glide.with(context)
            .load(arrayList[position].albumArt)
            .transform(BlurTransformation(35))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }


    fun delete(context: Context, imageFile: File) {
        val handler = Handler()
        handler.postDelayed({
            // Set up the projection (we only need the ID)
            val projection = arrayOf(MediaStore.Audio.Media._ID)

            // Match on the file path
            val selection = MediaStore.Audio.Media.DATA + " = ?"
            val selectionArgs = arrayOf<String>(imageFile.absolutePath)

            // Query for the ID of the media matching the file path
            val queryUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val contentResolver = context.contentResolver
            val c = contentResolver.query(queryUri, projection, selection, selectionArgs, null)
            if (c!!.moveToFirst()) {
                // We found the ID. Deleting the item via the content provider will also remove the file
                val id = c.getLong(c.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
                val deleteUri =
                    ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
                contentResolver.delete(deleteUri, null, null)

            } else {
                Log.w("Media ", "Media not found!!")

            }
            c.close()
        }, 70)
    }


    private fun deleteFile(context: Context,position: Int, view: View, song: SongModel) {
        val file = File(song.data)
        delete(context, file)
        /*musicAdapter.data.removeAt(position)
        musicAdapter.notifyItemRemoved(position)
        musicAdapter.notifyItemRangeChanged(position, musicAdapter.data.size)
        Snackbar.make(requireView(), "File Deleted", Snackbar.LENGTH_LONG).show()*/

    }

     fun onShowPopupMenu(context: Context,view: View, position: Int, song: SongModel) {
        val wrapper = ContextThemeWrapper(context, R.style.PopupMenu)
        val popupMenu = PopupMenu(wrapper, view)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {

                }
            }
            true
        }
    }
}