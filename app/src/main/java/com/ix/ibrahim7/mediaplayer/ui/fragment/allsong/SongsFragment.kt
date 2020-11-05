package com.ix.ibrahim7.mediaplayer.ui.fragment.allsong

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
import com.ix.ibrahim7.mediaplayer.adapter.SongAdapter
import com.ix.ibrahim7.mediaplayer.databinding.FragmentSongsBinding
import com.ix.ibrahim7.mediaplayer.model.SongModel
import com.ix.ibrahim7.mediaplayer.ui.fragment.MainFragmentDirections
import com.ix.ibrahim7.mediaplayer.ui.viewModel.SongViewModel
import kotlinx.android.synthetic.main.fragment_songs.*

class SongsFragment : Fragment(), SongAdapter.onClick  {


    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[SongViewModel::class.java]
    }

    lateinit var array: ArrayList<SongModel>

    private val song_adapter by lazy {
        SongAdapter(
            requireActivity(),
            ArrayList(),
            this
        )
    }


    private lateinit var mBinding: FragmentSongsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSongsBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.SongModelliveData.observe(viewLifecycleOwner, Observer {
            array = it
            list_song.apply {
                adapter = song_adapter
                song_adapter.data.clear()
                song_adapter.data.addAll(it)
               /* song_adapter.data.sortBy {
                    it.dateAdded
                }*/
                song_adapter.notifyDataSetChanged()
                layoutAnimation = AnimationUtils.loadLayoutAnimation(
                    requireContext(),
                    R.anim.recyclerview_layout_animation
                )
            }
             Log.e("Eee song",it.toString())
        })


    }


    override fun onClickItem(position: Int, type: Int) {
        when (type) {
            1 -> {
                var arr = arrayOfNulls<SongModel>(array.size)
                arr = array.toArray(arr)

               /* Log.e("eee song",arr[position]!!.album.toString())
                Log.e("eee song",arr[position]!!.artist.toString())
                Log.e("eee song",arr[position]!!.path.toString())
                Log.e("eee song",arr[position]!!.duration.toString())
                Log.e("eee song",arr[position]!!.title.toString())
                Log.e("eee song position",position.toString())*/

                val action = MainFragmentDirections.actionMainFragmentToPlayerFragment(
                    position.toString(),
                    arr.requireNoNulls()
                )
                findNavController().navigate(action)
            }
        }
    }


}