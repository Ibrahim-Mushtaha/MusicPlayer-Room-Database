package com.ix.ibrahim7.mediaplayer.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.databinding.ActivitySplashBinding
import com.ix.ibrahim7.mediaplayer.service.MusicService
import com.ix.ibrahim7.mediaplayer.ui.viewModel.SplashState
import com.ix.ibrahim7.mediaplayer.ui.viewModel.SplashViewModel
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[SplashViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        viewModel.liveData.observe(this, Observer {
            when (it) {
                is SplashState.MainActivity -> {
                    goToMainActivity()
                }
            }
        })

        val a: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        a.reset()

        splash_container.clearAnimation()
        splash_container.startAnimation(a)


    }


    private fun goToMainActivity() {
        startActivity(Intent(this, BasicActivity::class.java))
        finish()
    }



}