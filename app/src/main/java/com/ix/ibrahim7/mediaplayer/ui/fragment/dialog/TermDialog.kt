package com.ix.ibrahim7.mediaplayer.ui.fragment.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.databinding.DialogTermBinding
import kotlinx.android.synthetic.main.dialog_term.*

class TermDialog() : DialogFragment() {


    lateinit var mBinding:DialogTermBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogTermBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
            val win=dialog!!.window
            win!!.setGravity(Gravity.CENTER)
            win.attributes.windowAnimations = R.style.Dialog_Animation
            dialog!!.setCancelable(false)
        }


        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        imageButton.setOnClickListener {
            dismiss()
        }


        super.onViewCreated(view, savedInstanceState)
    }



    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }





}