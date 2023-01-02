package com.example.chatapp.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.chatapp.R
import com.example.chatapp.adapter.InduvChatAdapter
import com.example.chatapp.base.BaseFragmentWithBinding
import com.example.chatapp.databinding.FragmentChatBinding
import com.example.chatapp.databinding.FragmentInduvidualChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.example.chatapp.model.Message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class InduvChatFragment : BaseFragmentWithBinding<FragmentInduvidualChatBinding>() {

    private lateinit var db: FirebaseDatabase
    private lateinit var senderUid: String
    private lateinit var receiverUid: String

    private lateinit var senderRoom: String
    private lateinit var receiverRoom: String
    private lateinit var list: ArrayList<Message>

    val args: InduvChatFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        senderUid = FirebaseAuth.getInstance().uid.toString()
        receiverUid = FirebaseAuth.getInstance().uid.toString()

        val uid = args.userId

        senderRoom = senderUid + receiverUid
        receiverRoom = receiverUid + senderUid

        db = FirebaseDatabase.getInstance()

        binding.sendBtn.setOnClickListener {
            val msg = binding.msgEdt.text.toString()

            if (TextUtils.isEmpty(msg)) {
                Toast.makeText(context, "Please enter your messgae", Toast.LENGTH_LONG).show()
            } else {
                val currMsg = Message(msg, senderUid, Date().time)

                val randomKey = db.reference.push().key
                db.reference.child("chats")
                    .child(senderRoom).child("message").child(randomKey!!).setValue(currMsg).addOnSuccessListener {
                        db.reference.child("chats").child(receiverRoom).child("message").child(randomKey).setValue(currMsg).addOnSuccessListener {
                            binding.msgEdt.text = null
                            Toast.makeText(context, "Message sent", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        db.reference.child("chats").child(senderRoom).child("message")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()

                    for (itemsnapshot in snapshot.children) {
                        val data = itemsnapshot.getValue(Message::class.java)
                        list.add(data!!)
                    }
                    binding.msgsRv.adapter = InduvChatAdapter(list)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }

            })
    }

    companion object {

        fun newInstance(): InduvChatFragment {
            return InduvChatFragment()
        }
    }

    override fun getFragLayout(): Int {
        return R.layout.fragment_induvidual_chat
    }

}

/*
why is it blanking the editext in materialcardview thing man

 */