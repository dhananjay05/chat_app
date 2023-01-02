package com.example.chatapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapp.databinding.LayoutChatItemBinding
import com.example.chatapp.model.UserDto
import com.example.chatapp.ui.HomeFragmentDirections
import java.util.*

class ChatAdapter(var list: ArrayList<UserDto>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(val binding: LayoutChatItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = LayoutChatItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {

        val user = list[position]
        Glide.with(holder.itemView.context).load(user.imgUrl).into(holder.binding.profilePhotoIv)
        holder.binding.usernameTv.text = user.name

        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToInduvChatFragment(userId = user.uId.toString())
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = list.size
}