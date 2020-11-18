package com.ix.ibrahim7.mediaplayer.ui.fragment.Home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ferfalk.simplesearchview.SimpleSearchView
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.databinding.FragmentMainBinding
import com.ix.ibrahim7.mediaplayer.ui.viewModel.SongViewModel
import com.ix.ibrahim7.mediaplayer.util.Constant
import com.ix.ibrahim7.mediaplayer.viewpager.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_basic.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(){

    private val pager by lazy {
        SectionsPagerAdapter(requireContext(),childFragmentManager)
    }

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[SongViewModel::class.java]
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

        setUpViewPager()

    }



    fun setUpViewPager(){
        view_pager2.adapter = pager
        requireActivity().tabs.setupWithViewPager(view_pager2)
        pager.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.song, menu)
        val x= menu.findItem(R.id.search)
        requireActivity().searchView.setMenuItem(x)
        x.setOnMenuItemClickListener {
            val bundle = Bundle().apply {
                putInt(Constant.TYPE,2)
            }
            findNavController().navigate(R.id.action_mainFragment_to_allListFragment,bundle)
            return@setOnMenuItemClickListener true
        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.refresh->{
                Constant.showDialog(requireActivity())
                Handler().postDelayed(Runnable {
                    viewModel.getSongs(requireActivity().application)
                    Constant.dialog.dismiss()
                },1000)
            }
            R.id.name->{
                Constant.getSharePref(requireContext()).edit().putString(Constant.SORTTYPE,"name").apply()
                SortType("name")
            }
            R.id.data_added->{
                Constant.getSharePref(requireContext()).edit().putString(Constant.SORTTYPE,"data_added").apply()
                SortType("data_added")
            }
            R.id.artist->{
                Constant.getSharePref(requireContext()).edit().putString(Constant.SORTTYPE,"artist").apply()
                SortType("artist")

            }
        }
        return super.onOptionsItemSelected(item)
    }



    private fun SortType(type:String){
        Constant.getSharePref(requireContext()).edit().putString(Constant.SORTTYPE,type).apply()
        Constant.showDialog(requireActivity())
        Handler().postDelayed(Runnable {
            viewModel.getSongs(requireActivity().application)
            Constant.dialog.dismiss()
        },1000)
    }

}