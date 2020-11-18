package com.ix.ibrahim7.mediaplayer.ui.fragment.Home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.databinding.FragmentHomeBinding
import com.ix.ibrahim7.mediaplayer.service.MusicService
import com.ix.ibrahim7.mediaplayer.ui.fragment.Home.HomeFragmentDirections
import com.ix.ibrahim7.mediaplayer.ui.viewModel.HomeViewModel
import com.ix.ibrahim7.mediaplayer.ui.viewModel.PlayerViewModel
import com.ix.ibrahim7.mediaplayer.util.Constant.TYPE
import kotlinx.android.synthetic.main.activity_basic.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private var musicService: MusicService? = null

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    val intent by lazy {
        Intent(requireActivity(), MusicService::class.java)
    }


    private val viewModel2 by lazy {
        ViewModelProvider(this)[PlayerViewModel::class.java]
    }
    private lateinit var mBinding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().tabs.visibility = View.GONE
        requireActivity().toolbar.visibility = View.VISIBLE
        mBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getprimmision(requireContext())


        btn_allSongs.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMainFragment())
        }

        btn_recent_played.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(TYPE,0)
            }
            findNavController().navigate(R.id.action_homeFragment_to_allListFragment,bundle)
        }

        btn_favourite.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(TYPE,3)
            }
            findNavController().navigate(R.id.action_homeFragment_to_allListFragment,bundle)
        }

        btn_setting.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }


        btn_about.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_aboutFragment)
        }


    }


    override fun onDestroy() {

        super.onDestroy()
    }

}