package com.ix.ibrahim7.mediaplayer.ui.fragment.Main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.adapter.SongAdapter
import com.ix.ibrahim7.mediaplayer.databinding.FragmentSongsBinding
import com.ix.ibrahim7.mediaplayer.model.SongModel
import com.ix.ibrahim7.mediaplayer.ui.fragment.Home.MainFragmentDirections
import com.ix.ibrahim7.mediaplayer.ui.viewModel.SongViewModel
import com.ix.ibrahim7.mediaplayer.util.Constant
import kotlinx.android.synthetic.main.fragment_songs.*
import timber.log.Timber

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
       ,1 )
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
                song_adapter.data!!.clear()
                song_adapter.data!!.addAll(it)
                val type=Constant.getSharePref(requireContext()).getString(Constant.SORTTYPE,"name")
                song_adapter.data!!.sortBy {song->
                    if (type.equals("name")) {
                        song.title
                    }else if (type.equals("data_added")){
                        song.data
                    }else{
                        song.artistName
                    }
                }
                song_adapter.notifyDataSetChanged()
                layoutAnimation = AnimationUtils.loadLayoutAnimation(
                    requireContext(),
                    R.anim.recyclerview_layout_animation
                )
            }
            Timber.d("${Constant.TAG} $it")
        })


    }


    override fun onClickItem(position: Int, type: Int) {
        when (type) {
            1 -> {
                var arr = arrayOfNulls<SongModel>(array.size)
                arr = array.toArray(arr)

                val action = MainFragmentDirections.actionMainFragmentToPlayerFragment(
                    position.toString(),
                    arr.requireNoNulls()
                )
                findNavController().navigate(action)
            }

            2->{
                viewModel.addTofavorite(song_adapter.data!![position]).also {
                    Snackbar.make(mBinding.root, "added", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }


}