package com.ix.ibrahim7.mediaplayer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.mediaplayer.databinding.FragmentHomeBinding
import com.ix.ibrahim7.mediaplayer.ui.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.activity_basic.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {


    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
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


    }


}