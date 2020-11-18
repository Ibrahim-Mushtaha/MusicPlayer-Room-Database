package com.ix.ibrahim7.mediaplayer.ui.fragment.Home

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.databinding.FragmentPlayerBinding
import com.ix.ibrahim7.mediaplayer.model.SongModel
import com.ix.ibrahim7.mediaplayer.service.ActionPlaying
import com.ix.ibrahim7.mediaplayer.service.MusicService
import com.ix.ibrahim7.mediaplayer.service.MyNotificationManager
import com.ix.ibrahim7.mediaplayer.ui.fragment.Home.PlayerFragmentArgs
import com.ix.ibrahim7.mediaplayer.ui.viewModel.PlayerViewModel
import com.ix.ibrahim7.mediaplayer.util.Constant
import com.ix.ibrahim7.mediaplayer.util.Constant.ARRAY
import com.ix.ibrahim7.mediaplayer.util.Constant.POSITION
import com.ix.ibrahim7.mediaplayer.util.Constant.TAG
import com.ix.ibrahim7.mediaplayer.util.Constant.getBlurImage
import com.ix.ibrahim7.mediaplayer.util.Constant.getImage
import com.ix.ibrahim7.mediaplayer.util.Constant.millisecondsToTime
import kotlinx.android.synthetic.main.activity_basic.*
import kotlinx.android.synthetic.main.fragment_player.*
import timber.log.Timber
import kotlin.properties.Delegates


class PlayerFragment : Fragment(), ActionPlaying, ServiceConnection {

    val args: PlayerFragmentArgs by navArgs()

    private lateinit var mBinding: FragmentPlayerBinding

    private var playThread: Thread? = null
    private var prevThread: Thread? = null
    private var nextThread: Thread? = null

    lateinit var notificationManager: MyNotificationManager
    private lateinit var song: SongModel
    private lateinit var allSong: List<SongModel>

    private var musicService: MusicService? = null
    private var isShuffle = false
    private var isRepeat = false

    private var position by Delegates.notNull<Int>()
    private val handle = Handler()
    private var mediaSessionCompat: MediaSessionCompat? = null

    private val viewModel by lazy {
        ViewModelProvider(this)[PlayerViewModel::class.java]
    }


    val intent by lazy {
        Intent(requireActivity(), MusicService::class.java)
    }


    override fun onResume() {
        Timber.e("$TAG onResume")
        val intent = Intent(requireActivity(), MusicService::class.java).apply {
            putParcelableArrayListExtra(
                ARRAY,
                args.songArray.toMutableList() as ArrayList<SongModel>
            )
        }
        requireActivity().bindService(intent, this, Context.BIND_AUTO_CREATE)
        playThreadBtn()
        nextThreadBtn()
        prevThreadBtn()
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().tabs.visibility = View.GONE
        requireActivity().searchView.visibility = View.GONE
        position = args.postion.toInt()
        mBinding = FragmentPlayerBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationManager = MyNotificationManager(requireContext())

        requireArguments().apply {
            position = args.postion.toInt()
            song = args.songArray[position]!!
            allSong = args.songArray.toMutableList()
        }


        mediaSessionCompat = MediaSessionCompat(requireContext(), "My Audio")

        Timber.e("$TAG onViewCreated")


        btn_back.setOnClickListener {
            findNavController().navigateUp()
        }

        requireActivity().toolbar.title = ""
        requireActivity().toolbar.visibility = View.GONE

        initView(position)


    }

    private fun onHandle() {
        requireActivity().runOnUiThread(object : Runnable {
            override fun run() {
                if (musicService != null) {
                    try {
                        val mCurrentPosition = musicService!!.getCurrentPosition()
                        mBinding.seekbar.progress = mCurrentPosition
                    } catch (e: Exception) {

                    }

                }
                handle.postDelayed(this, 1000)
            }
        })
    }


    fun initSeekbar() {
        mBinding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                if (musicService != null && fromUser) {
                    musicService!!.seekTo(progress)
                }

                mBinding.etxtTotal.text = millisecondsToTime((song.duration - progress).toInt())
                mBinding.etxtStart.text = millisecondsToTime(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })
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




        getImage(requireContext(), args.songArray.toMutableList(), position, tv_image_player)
        getBlurImage(requireContext(), args.songArray.toMutableList(), position, dogBlurImageView)
        mBinding.btnPlay.setImageResource(R.drawable.ic_stop)
        initSeekbar()




        Constant.pattleImage(requireContext(),args.songArray.toMutableList(), position) { swatch ->
            if (swatch != null) {
                val grdient = GradientDrawable(
                    GradientDrawable.Orientation.BOTTOM_TOP,
                    intArrayOf(swatch.rgb, swatch.rgb)
                )
                mContanier.background = grdient
                txt_song_name.setTextColor(swatch.titleTextColor)
                txt_song_artist.setTextColor(swatch.bodyTextColor)
                txt.setTextColor(swatch.bodyTextColor)
                btn_back.drawable.setTint(swatch.bodyTextColor)
                point.setTextColor(swatch.bodyTextColor)
                etxt_start.setTextColor(swatch.bodyTextColor)
                etxt_total.setTextColor(swatch.bodyTextColor)
            }
        }

        intent.apply {
            putExtra(POSITION, position)
            putParcelableArrayListExtra(
                ARRAY,
                args.songArray.toMutableList() as ArrayList<SongModel>
            )
        }
        ActivityCompat.startForegroundService(requireContext(), intent)
        seekbar.max = song.duration.toInt()


        onHandle()

    }


    private fun playThreadBtn() {
        playThread = Thread {
            try {
                mBinding.btnPlay.setOnClickListener {
                    playPauseBtnClicked()
                }
            } catch (e: Exception) {

            }

        }
        playThread!!.start()
    }

    override fun playPauseBtnClicked() {
        if (musicService!!.isPlaying()) {
            mBinding.btnPlay.setImageResource(R.drawable.ic_play)
            musicService!!.pause()
            seekbar.max = musicService!!.getDuration()
            notificationManager.showNotification(
                requireContext(),
                R.drawable.ic_play,
                args.songArray.toMutableList() as ArrayList<SongModel>,
                position,
                musicService,
                mediaSessionCompat!!,
                false
            )
        } else {
            mBinding.btnPlay.setImageResource(R.drawable.ic_stop)
            musicService!!.start()
            seekbar.max = musicService!!.getDuration()
            notificationManager.showNotification(
                requireContext(),
                R.drawable.ic_stop,
                args.songArray.toMutableList() as ArrayList<SongModel>,
                position,
                musicService,
                mediaSessionCompat!!,
                true
            )
            Log.e("eee isPlaying", "not playing")

        }
        onHandle()
    }


    private fun nextThreadBtn() {
        nextThread = Thread {
            try {
                btn_next.setOnClickListener {
                    nextBtnClicked()
                }
            } catch (e: Exception) {

            }
        }
        nextThread!!.start()
    }

    override fun nextBtnClicked() {
        position = ((position + 1) % args.songArray.size)
        song = args.songArray[position]
        initView(position)
        notificationManager.showNotification(
            requireContext(),
            R.drawable.ic_stop,
            args.songArray.toMutableList() as ArrayList<SongModel>,
            position,
            musicService,
            mediaSessionCompat!!,
            true
        )
        viewModel.addToCurrentPlay(args.songArray.toMutableList() as ArrayList<SongModel>,position)
    }


    private fun prevThreadBtn() {
        prevThread = Thread {
            try {
                btn_previose.setOnClickListener {
                    prevBtnClicked()
                }
            } catch (e: Exception) {

            }
        }
        prevThread!!.start()
    }

    override fun prevBtnClicked() {
        position = if ((position - 1) < 0) (args.songArray.size - 1) else (position - 1)
        song = args.songArray[position]
        initView(position)
        notificationManager.showNotification(
            requireContext(),
            R.drawable.ic_stop,
            args.songArray.toMutableList() as ArrayList<SongModel>,
            position,
            musicService,
            mediaSessionCompat!!,
            true
        )
        viewModel.addToCurrentPlay(args.songArray.toMutableList() as ArrayList<SongModel>,position)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
        Toast.makeText(requireContext(), "Disconnected", Toast.LENGTH_LONG).show()
    }

    override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
        val myBinder = binder as MusicService.MyBinder
        musicService = myBinder.getService()
        musicService!!.musicFiles = args.songArray.toMutableList()
        musicService!!.setCallback(this)
        notificationManager.showNotification(
            requireContext(),
            R.drawable.ic_stop,
            args.songArray.toMutableList() as ArrayList<SongModel>,
            position,
            musicService,
            mediaSessionCompat!!,
            true
        )
        viewModel.addToCurrentPlay(args.songArray.toMutableList() as ArrayList<SongModel>,position)
    }


}