package com.ix.ibrahim7.mediaplayer.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.GradientDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.databinding.FragmentPlayerBinding
import com.ix.ibrahim7.mediaplayer.ui.viewModel.PlayerViewModel
import com.ix.ibrahim7.mediaplayer.util.Constant.STOP
import com.ix.ibrahim7.mediaplayer.util.Constant.getAlbumArt
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_basic.*
import kotlinx.android.synthetic.main.fragment_player.*
import kotlin.properties.Delegates


class PlayerFragment : Fragment() {

    val args: PlayerFragmentArgs by navArgs()

    lateinit var mediaPlayer: MediaPlayer

    private lateinit var mBinding: FragmentPlayerBinding

    var play = true

    private var length by Delegates.notNull<Int>()
    private var position by Delegates.notNull<Int>()
    private val handle = Handler()

    private val viewModel by lazy {
        ViewModelProvider(this)[PlayerViewModel::class.java]
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().tabs.visibility = View.GONE
        //  mediaPlayer.start()
        position = args.postion.toInt()
        mBinding = FragmentPlayerBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btn_back.setOnClickListener {
            findNavController().navigateUp()
        }

        requireActivity().toolbar.title = ""
        requireActivity().toolbar.visibility = View.GONE


        viewModel.startSong(args.songArray.toMutableList(), position)

        viewModel.dataMediaPlayerLiveData.observe(viewLifecycleOwner, Observer { Player ->
            mediaPlayer = Player
            mediaPlayer.setOnCompletionListener {
                try {
                    btn_play.setImageResource(R.drawable.ic_play)
                } catch (e: Exception) {

                }
                play = true
            }
            initSeekbar(mediaPlayer)
            onHandel(mediaPlayer)
        })


        viewModel.dataPositionLiveData.observe(viewLifecycleOwner, Observer { position ->
            initView(position)
        })

        viewModel.dataPlayLiveData.observe(requireActivity(), Observer {
            if (it == STOP) {
                btn_play.setImageResource(R.drawable.ic_play)
                length = mediaPlayer.currentPosition
            } else {
                btn_play.setImageResource(R.drawable.ic_stop)
                mediaPlayer.seekTo(length)
            }
        })


        btn_next.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.release()
                viewModel.NextPreviousSong(args.songArray.toMutableList(), position + 1)
                position += 1
            }else{
                viewModel.NextPreviousSong(args.songArray.toMutableList(), position + 1)
                position += 1
            }
        }

        btn_previose.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.release()
                viewModel.NextPreviousSong(args.songArray.toMutableList(), position - 1)
                position -= 1
            }else{
                viewModel.NextPreviousSong(args.songArray.toMutableList(), position + 1)
                position += 1
            }
        }


        btn_play.setOnClickListener {
            viewModel.StopSong()
        }




    }


    private fun onHandel(mediaPlayer: MediaPlayer) {
        requireActivity().runOnUiThread(object : Runnable {
            override fun run() {
                if (mediaPlayer != null) {
                    try {
                        val mCurrentPosition = mediaPlayer.currentPosition / 1000
                        mBinding.seekbar.progress = mCurrentPosition
                        etxt_start.setText(formatedTime(mCurrentPosition))
                        Handler().postDelayed(Runnable { }, 1000)
                    } catch (e: Exception) {

                    }
                }
                handle.postDelayed(this, 1000)
            }
        })
    }


    private fun formatedTime(mCurrent: Int): String? {
        var totalout = ""
        var totalNew = ""
        val seconds = (mCurrent % 60).toString()
        val minutes = (mCurrent / 60).toString()

        totalout = "$minutes:$seconds"
        totalNew = "$minutes:0$seconds"

        if (seconds.length == 1) {
            return totalNew
            Log.e("eeee totalNew", seconds)
            Log.e("eeee totalNew", minutes)
        } else {
            Log.e("eeee totalout", seconds)
            Log.e("eeee totalout", minutes)
            return totalout
        }
    }


    fun initSeekbar(mediaPlayer: MediaPlayer) {
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        seekbar.max = mediaPlayer.duration / 1000
    }


    fun initView(position: Int) {
        val durationTotal =
            Integer.parseInt((args.songArray.toMutableList()[position].duration.toInt() / 1000).toString())
        val seconds = (durationTotal % 60).toString()
        val minutes = (durationTotal / 60).toString()

        txt_song_name.text = args.songArray.toMutableList()[position].title
        txt_song_artist.text = args.songArray.toMutableList()[position].artistName
        txt.text = "Album - ${args.songArray.toMutableList()[position].albumName}"
        etxt_total.text = if (seconds.length == 1) {
            "$minutes:0$seconds"
        } else {
            "$minutes:$seconds"
        }

        Glide.with(requireActivity()).asBitmap()
            .load(args.songArray.toMutableList()[position].albumArt)
            .into(tv_image_player)

        Glide.with(requireActivity())
            .load(args.songArray.toMutableList()[position].albumArt)
            .transform(BlurTransformation(35))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(dogBlurImageView)


        val bitmap = BitmapFactory.decodeByteArray(
            getAlbumArt(args.songArray[position].data),
            0,
            getAlbumArt(args.songArray[position].data)!!.size
        )

        if (bitmap != null) {
            Palette.from(bitmap).generate { palette ->
                var swatch = palette!!.dominantSwatch
                if (swatch != null) {
                    val grdient = GradientDrawable(
                        GradientDrawable.Orientation.BOTTOM_TOP,
                        intArrayOf(swatch.rgb, swatch.rgb)
                    )
                    mContanier.setBackground(grdient)
                    txt_song_name.setTextColor(swatch.titleTextColor)
                    txt_song_artist.setTextColor(swatch.bodyTextColor)
                    txt.setTextColor(swatch.bodyTextColor)
                    btn_back.drawable.setTint(swatch.bodyTextColor)
                    point.setTextColor(swatch.bodyTextColor)
                    etxt_start.setTextColor(swatch.bodyTextColor)
                    etxt_total.setTextColor(swatch.bodyTextColor)
                    swatch = null
                }
            }
        }

    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
        play = true
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer.stop()
    }


}