package com.ix.ibrahim7.mediaplayer.ui.fragment.Home

import android.os.Bundle
import android.view.Display
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.databinding.FragmentSettingsBinding
import com.ix.ibrahim7.mediaplayer.databinding.FragmentSongsBinding
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment() {


    private lateinit var mBinding: FragmentSettingsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSettingsBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            return@setOnCheckedChangeListener
        }


        super.onViewCreated(view, savedInstanceState)
    }


}