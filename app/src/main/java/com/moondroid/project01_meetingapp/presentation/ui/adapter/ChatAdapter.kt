package com.moondroid.project01_meetingapp.presentation.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.moondroid.damoim.domain.model.ChatItem
import com.moondroid.project01_meetingapp.databinding.ItemChatBinding
import kotlin.properties.Delegates

class ChatAdapter(private val context: Context) :BaseAdapter(){

    private var chatList: List<ChatItem> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun update(newChatList: List<ChatItem>) {
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