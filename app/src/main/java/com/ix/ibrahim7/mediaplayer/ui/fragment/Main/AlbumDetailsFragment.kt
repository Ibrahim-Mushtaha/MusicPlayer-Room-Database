package com.ix.ibrahim7.mediaplayer.ui.fragment.Main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.adapter.AlbumAdapter
import com.ix.ibrahim7.mediaplayer.adapter.SongAdapter
import com.ix.ibrahim7.mediaplayer.databinding.FragmentAlbumDetailsBinding
import com.ix.ibrahim7.mediaplayer.model.AlbumModel
import com.ix.ibrahim7.mediaplayer.model.SongModel
import com.ix.ibrahim7.mediaplayer.ui.fragment.Main.AlbumDetailsFragmentDirections
import com.ix.ibrahim7.mediaplayer.ui.viewModel.AlbumArtistViewModel
import com.ix.ibrahim7.mediaplayer.util.Constant
import kotlinx.android.synthetic.main.activity_basic.*
import kotlinx.android.synthetic.main.activity_basic.toolbar
import kotlinx.android.synthetic.main.fragment_album_details.*


class AlbumDetailsFragment : Fragment(), SongAdapter.onClick, AlbumAdapter.onClick {

    private lateinit var mBinding: FragmentAlbumDetailsBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[AlbumArtistViewModel::class.java]
    }

    private val data by lazy {
        requireArguments().getParcelableArrayList<SongModel>("array")
    }

    private val data_album by lazy {
        requireArguments().getParcelableArrayList<AlbumModel>("array")
    }

    var array = ArrayList<SongModel>()
    private val type by lazy {
        requireArguments().getInt("type")
    }

    private val song_adapter by lazy {
        SongAdapter(
            requireActivity(),
            ArrayList(),
            object : SongAdapter.onClick {
                override fun onClickItem(position: Int, type: Int) {
                    when (type) {
                        1 -> {
                            var arr = arrayOfNulls<SongModel>(array.size)
                            arr = array.toArray(arr)

                            Constant.Move(
                                findNavController(),
                                AlbumDetailsFragmentDirections.actionAlbumDetailsFragmentToPlayerFragment(
                                    position.toString(),
                                    arr.requireNoNulls()
                                )
                            )
                        }
                    }
                }

            }
        ,0)
    }

    private val song_adapter2 by lazy {
        AlbumAdapter(
            requireActivity(),
            ArrayList(),
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().tabs.visibility = View.GONE
        requireActivity().toolbar.visibility = View.GONE
        requireActivity().searchView.visibility = View.GONE
        mBinding = FragmentAlbumDetailsBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar_album.setNavigationOnClickListener {
            findNavController().navigateUp()
        }


        if (type == 1) {
            Constant.getImage(requireContext(),data as ArrayList<SongModel>,0,imageStore)
            list_artist_album.adapter = song_adapter
            song_adapter.data!!.clear()
            song_adapter.data!!.addAll(data!!)
            array.addAll(data!!)
            Log.e("eee song", "song")
            song_adapter.notifyDataSetChanged()
        } else {
            Glide.with(requireActivity()).load(data_album!![0].coverArt)
                .error(R.drawable.ic_album_cover).into(imageStore)
            list_artist_album.adapter = song_adapter2
            Log.e("eee album", "album")
            song_adapter2.data.clear()
            song_adapter2.data.addAll(data_album!!)
            song_adapter2.notifyDataSetChanged()
        }
    }

    override fun onClickItem(position: Int, type: Int) {
        when(type) {
            1 -> {
                val bundle = Bundle().apply {
                    putInt(Constant.TYPE,1)
                    putParcelableArrayList(
                        Constant.ARRAY,
                        song_adapter2.data.toMutableList()[position].albumSongs
                    )
                }
                findNavController().navigate(R.id.action_albumDetailsFragment_to_allListFragment,bundle)

                }
        }
        }


}