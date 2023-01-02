package com.example.chatapp.ui

import com.example.chatapp.R
import com.example.chatapp.base.BaseFragmentWithBinding
import com.example.chatapp.databinding.FragmentCallBinding

class CallFragment : BaseFragmentWithBinding<FragmentCallBinding>() {


    companion object {
        fun newInstance(): CallFragment {
            return CallFragment()
        }
    }

    override fun getFragLayout(): Int {
        return R.layout.fragment_call
    }
}