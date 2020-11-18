package com.ix.ibrahim7.mediaplayer.ui.fragment.Main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.adapter.AlbumAdapterGrid
import com.ix.ibrahim7.mediaplayer.databinding.FragmentAlbumBinding
import com.ix.ibrahim7.mediaplayer.ui.viewModel.AlbumViewModel
import com.ix.ibrahim7.mediaplayer.util.Constant
import kotlinx.android.synthetic.main.fragment_album.*

class AlbumFragment : Fragment(), AlbumAdapterGrid.onClick {


    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[AlbumViewModel::class.java]
    }


    lateinit var array: ArrayList<String>

    private val album_adapter by lazy {
        AlbumAdapterGrid(
            requireActivity(),
            ArrayList(),
            this
        )
    }


    private lateinit var mBinding: FragmentAlbumBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAlbumBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.AlbumliveData.observe(viewLifecycleOwner, Observer {
            list_album.apply {
                adapter = album_adapter
                album_adapter.data.clear()
                album_adapter.data.addAll(it)
                album_adapter.notifyDataSetChanged()
            }
            list_album.layoutAnimation = AnimationUtils.loadLayoutAnimation(
                requireContext(),
                R.anim.recyclerview_layout_animation
            )
        })
    }

    override fun onClickItem(position: Int, type: Int) {
        when(type){
            1->{
                album_adapter.data[position].albumSongs.forEach {
                    Log.e("eee album",it.title)
                    Log.e("eee album",it.albumId.toString())
                }
                val bundle = Bundle().apply {
                    putParcelableArrayList(Constant.ARRAY,
                        album_adapter.data[position].albumSongs
                    )
                    putInt("type",1)
                }
                findNavController().navigate(R.id.action_mainFragment_to_albumDetailsFragment,bundle)
            }
        }
    }

}