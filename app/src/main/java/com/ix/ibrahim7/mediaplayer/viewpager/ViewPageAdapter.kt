package com.example.groceryshoppingapp.util.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter



class ViewPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var lf = mutableListOf<Fragment>()
    var lt = mutableListOf<String>()

    override fun getItem(i: Int): Fragment {
        return lf[i]
    }

    override fun getCount(): Int {
        return lt.size
    }

    override fun getPageTitle(i: Int): CharSequence? {
        return lt[i]
    }

    // Function For Add Fragments in ViewPager
    fun addFragment(f: Fragment, t: String) {
        lf.add(f)
        lt.add(t)
    }


}