package com.example.chatapp.ui

import android.os.Bundle
import android.os.Message
import android.text.TextUtils
import android.widget.Toast
import androidx.core.text.trimmedLength
import com.example.chatapp.databinding.FragmentChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class ChatFragment : BaseFragmentWithBinding<FragmentChatBinding>() {

    private lateinit var db: FirebaseDatabase
    private lateinit var senderUid: String
    private lateinit var receiverUid: String

    private lateinit var senderRoom: String
    private lateinit var receiverRoom: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        senderUid = FirebaseAuth.getInstance().uid.toString()
        receiverUid = FirebaseAuth.getInstance().uid.toString()

        //get uid paramter

        senderRoom = senderUid + receiverUid
        receiverRoom = receiverUid + senderUid

        db = FirebaseDatabase.getInstance()

        binding.sendBtn.setOnClickListener {
            val msg = binding.msgEdt.text.toString()

            if (TextUtils.isEmpty(msg)) {
                Toast.makeText(context, "Please enter your messgae", Toast.LENGTH_LONG).show()
            } else {
                val currMsg = com.example.chatapp.model.Message(msg, senderUid, Date().time)

                val randomKey = db.reference.push().key
                db.reference.child("chats")
                    .child(senderRoom).child(msg).child(randomKey!!).setValue(msg).addOnSuccessListener {
                        db.reference.child("chats").child(receiverRoom).child(msg).child(randomKey!!).setValue(msg).addOnSuccessListener {
                            binding.msgEdt.text = null
                        }
                    }
            }
        }
    }

}

/*
why is it blanking the editext in materialcardview thing man

 */