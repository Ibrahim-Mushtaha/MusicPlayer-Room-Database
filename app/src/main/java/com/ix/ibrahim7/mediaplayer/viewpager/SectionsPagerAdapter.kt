package com.ix.ibrahim7.mediaplayer.viewpager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.ui.fragment.allsong.AlbumFragment
import com.ix.ibrahim7.mediaplayer.ui.fragment.allsong.ArtistsFragment
import com.ix.ibrahim7.mediaplayer.ui.fragment.allsong.SongsFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        var fragment: Fragment? =null

        when (position) {
            0 -> fragment =
                SongsFragment()
            1-> fragment =
                AlbumFragment()
            2-> fragment =
                ArtistsFragment()
        }

        return fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 3
    }
}