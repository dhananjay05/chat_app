package com.example.chatapp.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.gms.common.util.CollectionUtils.listOf
import java.util.*

class ViewpagerAdapter(
    private val context: Context,
    fa: FragmentManager,
    val list: ArrayList<Fragment>
) : FragmentPagerAdapter(fa) {
    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]

    }

    companion object {
        val TAB_TITLES = listOf(
            "Chat", "Status", "Call"
        )
    }

}