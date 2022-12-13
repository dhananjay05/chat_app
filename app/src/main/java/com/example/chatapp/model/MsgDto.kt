package com.example.chatapp.model

data class Message(
    val msg: String? = "",
    val senderId: String? = "",
    val timestamp: Long? = 0
)