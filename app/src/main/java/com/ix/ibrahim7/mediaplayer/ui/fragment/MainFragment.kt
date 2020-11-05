package com.ix.ibrahim7.mediaplayer.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.databinding.FragmentMainBinding
import com.ix.ibrahim7.mediaplayer.viewpager.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_basic.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(){




    private val pager by lazy {
        SectionsPagerAdapter(requireContext(),childFragmentManager)
    }


    private lateinit var mBinding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().tabs.visibility = View.VISIBLE
        requireActivity().toolbar.visibility = View.VISIBLE
        setHasOptionsMenu(true)
        mBinding = FragmentMainBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


      /*  if (pager.lt.size == 0 && pager.lf.size == 0) {
            pager.addFragment(SongsFragment(), "Songs")
            pager.addFragment(AlbumFragment(), "Albums")
            pager.addFragment(ArtistsFragment(), "Artists")
        }*/

        view_pager2.adapter = pager
        requireActivity().tabs.setupWithViewPager(view_pager2)
        pager.notifyDataSetChanged()



    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.song, menu)
    }

}