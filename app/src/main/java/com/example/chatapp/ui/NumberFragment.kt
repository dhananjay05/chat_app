package com.example.chatapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentNumberBinding
import com.google.firebase.auth.FirebaseAuth


class NumberFragment : BaseFragmentWithBinding<FragmentNumberBinding>() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            //redirect to  main class
        }

        binding.btnContinue.setOnClickListener {
            if (binding.phoneNoEdt.text?.length!! < 10) {
                Toast.makeText(context, "Please enter valid number", Toast.LENGTH_LONG).show()
            } else {
                //migrate to otp activity
            }
        }
    }
}