package com.moondroid.project01_meetingapp.ui.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.moondroid.project01_meetingapp.databinding.ItemChatBinding
import com.moondroid.project01_meetingapp.model.Chat
import kotlin.properties.Delegates

class ChatAdapter(private val context: Context) :BaseAdapter(){

    private var chatList: List<Chat> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun update(newChatList: List<Chat>) {
        chatList = newChatList
    }

    override fun getCount(): Int {
        return chatList.size
    }

    override fun getItem(p0: Int): Any {
        return chatList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(context), p2, false)
        binding.chat = chatList[p0]
        return binding.root
    }
}