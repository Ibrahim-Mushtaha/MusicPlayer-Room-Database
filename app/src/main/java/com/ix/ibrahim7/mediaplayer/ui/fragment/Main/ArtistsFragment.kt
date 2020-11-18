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
import com.ix.ibrahim7.mediaplayer.adapter.ArtistAdapter
import com.ix.ibrahim7.mediaplayer.databinding.FragmentArtistsBinding
import com.ix.ibrahim7.mediaplayer.model.ArtistModel
import com.ix.ibrahim7.mediaplayer.ui.viewModel.ArtistViewModel
import com.ix.ibrahim7.mediaplayer.util.Constant
import kotlinx.android.synthetic.main.fragment_artists.*


class ArtistsFragment : Fragment(),ArtistAdapter.onClick {

    private lateinit var mBinding: FragmentArtistsBinding

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[ArtistViewModel::class.java]
    }

    lateinit var array: ArrayList<ArtistModel>

    private val artist_adapter by lazy {
        ArtistAdapter(
            requireActivity(),
            ArrayList(),
            this
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentArtistsBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.ArtistliveData.observe(viewLifecycleOwner, Observer {
            array = it
            list_artist.apply {
                adapter = artist_adapter
                artist_adapter.data.clear()
                artist_adapter.data.addAll(it)
                artist_adapter.notifyDataSetChanged()
                layoutAnimation = AnimationUtils.loadLayoutAnimation(
                    requireContext(),
                    R.anim.recyclerview_layout_animation
                )
            }
            Log.e("Eee song",it.toString())
        })



    }

    override fun onClickItem(position: Int, type: Int) {
        when(type) {
         1-> {
             val bundle = Bundle().apply {
                 putParcelableArrayList(
                     Constant.ARRAY,
                     artist_adapter.data[position].albums
                 )
                 putInt("type",0)
             }
             findNavController().navigate(R.id.action_mainFragment_to_albumDetailsFragment, bundle)
         }
        }
    }

}