package com.ix.ibrahim7.mediaplayer.ui.fragment.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.databinding.FragmentAboutBinding
import com.ix.ibrahim7.mediaplayer.databinding.FragmentSettingsBinding
import com.ix.ibrahim7.mediaplayer.ui.fragment.dialog.TermDialog
import kotlinx.android.synthetic.main.fragment_about.*


class AboutFragment : Fragment() {


    private lateinit var mBinding: FragmentAboutBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAboutBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        btn_term.setOnClickListener {
            TermDialog().show(childFragmentManager,"")
        }


        super.onViewCreated(view, savedInstanceState)
    }

}