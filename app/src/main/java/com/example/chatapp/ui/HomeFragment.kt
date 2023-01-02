package com.example.chatapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.chatapp.R
import com.example.chatapp.adapter.HomePagerAdapter
import com.example.chatapp.base.BaseFragmentWithBinding
import com.example.chatapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : BaseFragmentWithBinding<FragmentHomeBinding>() {

    val fragmentArrayList = ArrayList<Fragment>()
    private var adapter: HomePagerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tablayout = binding.appTabs
        val viewpager = binding.viewpager
        fragmentArrayList.add(ChatFragment.newInstance())
        fragmentArrayList.add(StatusFragment.newInstance())

        adapter = HomePagerAdapter(requireActivity(), fragmentArrayList)
        viewpager.adapter = adapter

        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()
    }

    companion object {
        private val TAB_TITLES = listOf(
            "Chat", "Status", "Call"
        )
    }

    override fun getFragLayout(): Int {
        return R.layout.fragment_home
    }
}