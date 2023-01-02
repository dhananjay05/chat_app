package com.example.chatapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.gms.common.util.CollectionUtils.listOf
import java.util.*

class HomePagerAdapter(
    fa: FragmentActivity,
    val list: ArrayList<Fragment>
) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        if (list != null)
            return list.size
        return 0
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }


    companion object {
        val TAB_TITLES = listOf(
            "Chat", "Status", "Call"
        )
    }

}