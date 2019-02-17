package com.winsonmac.core.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.ArrayList


/* =========================================================================================== */
/*  Base pager adapter for easy implementation                                                 */
/* =========================================================================================== */

open class BasePagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private var fragments: MutableList<Fragment> = ArrayList()

    override fun getItem(i: Int): Fragment {
        return fragments[i]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }

    fun addAllFragments(fragments: MutableList<Fragment>) {
        fragments.addAll(fragments)
    }
}