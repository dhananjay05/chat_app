package com.example.chatapp.ui

import com.example.chatapp.R
import com.example.chatapp.base.BaseFragmentWithBinding
import com.example.chatapp.databinding.FragmentStatusBinding

class StatusFragment : BaseFragmentWithBinding<FragmentStatusBinding>() {

    companion object {
        fun newInstance(): StatusFragment {
            return StatusFragment()
        }
    }

    override fun getFragLayout(): Int {
        return R.layout.fragment_status
    }
}