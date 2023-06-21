package com.moondroid.project01_meetingapp.presentation.ui.group.chat

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.moondroid.damoim.domain.model.ChatItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.FragmentGroupChatBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseFragment
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.group.GroupActivity
import java.text.SimpleDateFormat

class ChatFragment : BaseFragment(R.layout.fragment_group_chat) {
    private val binding by viewBinding(FragmentGroupChatBinding::bind)

    private lateinit var activity: GroupActivity
    private lateinit var firebaseDB: FirebaseDatabase
    private lateinit var chatRef: DatabaseReference
    private val chatList: ArrayList<ChatItem> = ArrayList()
    private lateinit var chat: ChatItem
    private lateinit var adapter: ChatAdapter
    private lateinit var user: Profile

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as GroupActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.isMember = DMApp.group.isMember
        initView()
    }

    @SuppressLint("SimpleDateFormat")
    private fun initView() {
        binding.btnSend.setOnClickListener {
            val time = System.currentTimeMillis()

            val chat = ChatItem(
                user.id,
                user.name,
                SimpleDateFormat("MM.dd HH:ss").format(time),
                user.thumb,
                binding.etMessage.text.toString()
            )

            chatRef.let {
                it.child(time.toString()).setValue(chat).addOnSuccessListener {
                    binding.etMessage.setText("")
                }
            }
        }
        adapter = ChatAdapter(requireActivity())
        binding.list.adapter = adapter

        firebaseDB = FirebaseDatabase.getInstance()
        chatRef = firebaseDB.getReference("chat/" + DMApp.group.title)
        chatRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                chat = snapshot.getValue(ChatItem::class.java)!!
                DMApp.group.member.forEach {
                    if (chat.id == it.id) {
                        chat.thumb = it.thumb
                        chat.name = it.name
                        if (chat.id == user.id) {
                            chat.other = false
                        }
                        return@forEach
                    }
                }
                chatList.add(chat)
                adapter.update(chatList)
                binding.list.setSelection(chatList.size - 1)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}