package com.example.chatapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapp.databinding.LayoutChatItemBinding
import com.example.chatapp.model.UserDto
import java.util.*

class ChatAdapter(var context: Context, var list: ArrayList<UserDto>) :
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
        Glide.with(context).load(user.imgUrl).into(holder.binding.profilePhotoIv)
        holder.binding.usernameTv.text = user.name

        holder.itemView.setOnClickListener {
            //open chatfragment using jetpack navigation
        }
    }

    override fun getItemCount() = list.size
}