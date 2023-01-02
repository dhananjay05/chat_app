package com.example.chatapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chatapp.R
import com.example.chatapp.adapter.ChatAdapter
import com.example.chatapp.base.BaseFragmentWithBinding
import com.example.chatapp.databinding.FragmentChatBinding
import com.example.chatapp.model.UserDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatFragment : BaseFragmentWithBinding<FragmentChatBinding>() {

    private var db: FirebaseDatabase? = null
    lateinit var userList: ArrayList<UserDto>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        db = FirebaseDatabase.getInstance()
        userList = ArrayList()

        db?.reference?.child("users")
            ?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userList.clear()

                    for (itemSnapshot in snapshot.children) {
                        val user = itemSnapshot.getValue(UserDto::class.java)
                        if (user?.uId != FirebaseAuth.getInstance().uid) {
                            user?.let { userList.add(it) }
                        }
                    }

                    binding.chatsRv.adapter = ChatAdapter(userList)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getFragLayout(): Int {
        return R.layout.fragment_chat
    }

    companion object {
        fun newInstance(): ChatFragment {
            return ChatFragment()
        }
    }
}