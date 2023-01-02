package com.example.chatapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.databinding.LayoutReceiveItemBinding
import com.example.chatapp.databinding.LayoutSendItemBinding
import com.example.chatapp.model.Message
import com.google.firebase.auth.FirebaseAuth

class InduvChatAdapter(var list: ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class SentItemVH(val binding : LayoutSendItemBinding) : RecyclerView.ViewHolder(binding.root)

    inner class ReceiveItemVH(val binding: LayoutReceiveItemBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        const val ITEM_SENT = 1
        const val ITEM_RECEIVE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_RECEIVE) {
            val binding = LayoutReceiveItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ReceiveItemVH(binding)
        } else {
            val binding = LayoutSendItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return SentItemVH(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (FirebaseAuth.getInstance().uid == list[position].senderId) ITEM_SENT else ITEM_RECEIVE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = list[position]
        if (holder.itemViewType == ITEM_SENT) {
            val viewHolder = holder as SentItemVH
            viewHolder.binding.msgSent.text = msg.msg
        } else {
            val viewHolder = holder as ReceiveItemVH
            viewHolder.binding.msgReceive.text = msg.msg
        }
    }

    override fun getItemCount() = list.size
}