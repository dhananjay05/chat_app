package com.example.chatapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.base.BaseFragmentWithBinding
import com.example.chatapp.databinding.FragmentNumberBinding
import com.google.firebase.auth.FirebaseAuth


class NumberFragment : BaseFragmentWithBinding<FragmentNumberBinding>() {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            navigateToHome()
        }

        binding.btnContinue.setOnClickListener {
            if (binding.phoneNoEdt.text?.length!! < 10) {
                Toast.makeText(context, "Please enter valid number", Toast.LENGTH_LONG).show()
            } else {
                val number = binding.phoneNoEdt.text.toString()
                val action = NumberFragmentDirections.actionNumberFragmentToOTPFragment(number)
                findNavController().navigate(action)
            }
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_numberFragment_to_homeFragment)
    }

    override fun getFragLayout(): Int {
        return R.layout.fragment_number
    }
}