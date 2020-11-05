package com.ix.ibrahim7.mediaplayer.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.adapter.AlbumAdapter
import com.ix.ibrahim7.mediaplayer.adapter.AlbumAdapterGrid
import com.ix.ibrahim7.mediaplayer.adapter.SongAdapter
import com.ix.ibrahim7.mediaplayer.databinding.FragmentAlbumDetailsBinding
import com.ix.ibrahim7.mediaplayer.model.AlbumModel
import com.ix.ibrahim7.mediaplayer.model.SongModel
import com.ix.ibrahim7.mediaplayer.ui.viewModel.AlbumArtistViewModel
import kotlinx.android.synthetic.main.activity_basic.*
import kotlinx.android.synthetic.main.activity_basic.toolbar
import kotlinx.android.synthetic.main.fragment_album_details.*


class AlbumDetailsFragment : Fragment(), SongAdapter.onClick,AlbumAdapter.onClick {

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

    private val type by lazy {
        requireArguments().getInt("type")
    }

    private val song_adapter by lazy {
        SongAdapter(
            requireActivity(),
            ArrayList(),
            this
        )
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
            Glide.with(requireActivity()).load(data!![0].albumArt).error(R.drawable.ic_album_cover).into(imageStore)
            list_artist_album.adapter = song_adapter
            song_adapter.data.addAll(data!!)
            song_adapter.notifyDataSetChanged()
        } else {
            Glide.with(requireActivity()).load(data_album!![0].coverArt).error(R.drawable.ic_album_cover).into(imageStore)
            list_artist_album.adapter = song_adapter2
            song_adapter2.data.addAll(data_album!!)
            song_adapter2.notifyDataSetChanged()
        }


    }

    override fun onClickItem(position: Int, type: Int) {

    }


}