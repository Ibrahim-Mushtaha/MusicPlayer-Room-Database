package com.ix.ibrahim7.mediaplayer.ui.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.databinding.ActivityBasicBinding
import com.ix.ibrahim7.mediaplayer.service.MusicService
import kotlinx.android.synthetic.main.activity_basic.*

class BasicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBasicBinding


    lateinit var   appBarConfiguration : AppBarConfiguration
    lateinit var   navController : NavController
    lateinit var drawerLayout: DrawerLayout



    val intent2 by lazy {
        Intent(this, MusicService::class.java)
    }



    private var musicService: MusicService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasicBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(toolbar)

        if (intent.getBooleanExtra("run",false)){
            Log.e("eeee run","true")
          //  musicService!!.mediaPlayer!!.stop()
         //   musicService!!.mediaPlayer!!.release()
            musicService!!.stopForeground(true)
            musicService!!.mediaPlayer = null
            stopService(Intent(this, MusicService::class.java))
            // MusicService().mediaPlayer.stop()
            //     stopService(Intent(this,MusicService::class.java))
        }else{
            Log.e("eeee run","false")
        }

     //   drawerLayout = findViewById(R.id.drawer_layout)
        navController = findNavController(R.id.fragment_nav_host_home)
        appBarConfiguration= AppBarConfiguration(setOf(
            R.id.homeFragment
        ))

    /*    navigation_view.setupWithNavController(navController)

        navigation_view.setBackgroundColor(Color.parseColor("#10121D"))*/

        setupActionBarWithNavController(navController, appBarConfiguration)

        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController,appBarConfiguration)

      /*  btn_switch.setOnClickListener {
            if (btn_switch.isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }*/




    }

    override fun onDestroy() {
        musicService!!.stop()
        musicService!!.release()
        musicService!!.stopForeground(true)
        musicService!!.mediaPlayer = null
        stopService(intent2)
        super.onDestroy()
    }
}