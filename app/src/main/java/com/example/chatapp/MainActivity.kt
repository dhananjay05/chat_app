package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.chatapp.adapter.ViewpagerAdapter
import com.example.chatapp.databinding.ActivityMainBinding
import com.example.chatapp.ui.CallFragment
import com.example.chatapp.ui.ChatFragment
import com.example.chatapp.ui.StatusFragment
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentList = ArrayList<Fragment>()

        fragmentList.add(ChatFragment())
        fragmentList.add(StatusFragment())
        fragmentList.add(CallFragment())

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            //navigate to number fragment
        }

        val adapter = ViewpagerAdapter(this, supportFragmentManager, fragmentList)
    }
}