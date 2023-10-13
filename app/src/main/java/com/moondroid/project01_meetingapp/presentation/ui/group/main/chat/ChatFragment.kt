package com.moondroid.project01_meetingapp.presentation.ui.group.main.chat

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.moondroid.damoim.common.Extension.debug
import com.moondroid.damoim.domain.model.ChatItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.group.GetMembersUseCase
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.FragmentGroupChatBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseFragment
import com.moondroid.project01_meetingapp.presentation.ui.group.main.GroupActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject


@AndroidEntryPoint
class ChatFragment : BaseFragment(R.layout.fragment_group_chat) {
    private var _binding: FragmentGroupChatBinding? = null
    private val binding: FragmentGroupChatBinding get() = _binding!!

    private lateinit var activity: GroupActivity
    private lateinit var firebaseDB: FirebaseDatabase
    private lateinit var chatRef: DatabaseReference
    private val chatList: ArrayList<ChatItem> = ArrayList()
    private lateinit var chat: ChatItem
    private lateinit var adapter: ChatAdapter

    @Inject
    lateinit var membersUseCase: GetMembersUseCase
    private lateinit var members: List<Profile>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as GroupActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.isMember = DMApp.group.isMember
        binding.isMember = activity.userType != GroupActivity.UserType.VISITOR
        CoroutineScope(Dispatchers.Main).launch {
            membersUseCase(DMApp.group.title).collect { result ->
                result.onSuccess {
                    members = it
                    initView()
                }.onError {
                    activity.networkError(it)
                }.onFail {
                    activity.serverError(it)
                }
            }
        }
    }

    private var changeFocus = false

    private val keyBoardListener = OnGlobalLayoutListener {
        val rect = Rect()
        binding.root.getWindowVisibleDisplayFrame(rect)
        val heightDiff: Int = binding.root.rootView.height - (rect.bottom - rect.top)
        debug("heightDiff $heightDiff")
        if (heightDiff > 500) {
            if (!changeFocus) {
                changeFocus = true
                binding.list.setSelection(chatList.lastIndex)
            }
        } else {
            changeFocus = false
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun initView() {
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(keyBoardListener)

        binding.btnSend.setOnClickListener {
            val time = System.currentTimeMillis()

            val chat = ChatItem(
                DMApp.profile.id,
                DMApp.profile.name,
                SimpleDateFormat("MM.dd HH:ss").format(time),
                DMApp.profile.thumb,
                binding.etMessage.text.toString()
            )

            chatRef.let {
                binding.etMessage.setText("")
                it.child(time.toString()).setValue(chat)
            }
        }
        adapter = ChatAdapter(requireActivity())
        binding.list.adapter = adapter

        firebaseDB = FirebaseDatabase.getInstance()
        chatRef = firebaseDB.getReference("chat/" + DMApp.group.title)
        chatRef.addChildEventListener(eventListener)
    }

    private val eventListener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            chat = snapshot.getValue(ChatItem::class.java)!!
            members.forEach {
                if (chat.id == it.id) {
                    chat.thumb = it.thumb
                    chat.name = it.name
                    chat.other = chat.id != DMApp.profile.id
                    return@forEach
                }
            }
            chatList.add(chat)
            adapter.update(chatList)
            binding.list.setSelection(chatList.lastIndex)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        chatRef.removeEventListener(eventListener)
        binding.root.viewTreeObserver.removeOnGlobalLayoutListener(keyBoardListener)
        _binding = null
    }
}