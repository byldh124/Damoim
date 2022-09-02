package com.moondroid.project01_meetingapp.ui.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseFragment
import com.moondroid.project01_meetingapp.databinding.FragmentGroupBoardBinding
import com.moondroid.project01_meetingapp.databinding.FragmentGroupChatBinding
import com.moondroid.project01_meetingapp.databinding.FragmentGroupGalleryBinding
import com.moondroid.project01_meetingapp.databinding.FragmentGroupInfoBinding
import com.moondroid.project01_meetingapp.model.Chat
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.model.Moim
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.ui.view.activity.GroupActivity
import com.moondroid.project01_meetingapp.ui.view.adapter.ChatAdapter
import com.moondroid.project01_meetingapp.ui.view.adapter.MemberAdapter
import com.moondroid.project01_meetingapp.ui.view.adapter.MoimAdapter
import com.moondroid.project01_meetingapp.ui.viewmodel.GroupViewModel
import com.moondroid.project01_meetingapp.utils.DMLog
import com.moondroid.project01_meetingapp.utils.ResponseCode
import com.moondroid.project01_meetingapp.utils.view.gone
import com.moondroid.project01_meetingapp.utils.view.log
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class InfoFragment : BaseFragment<FragmentGroupInfoBinding>(R.layout.fragment_group_info) {

    private var activity: GroupActivity? = null
    private val viewModel: GroupViewModel by viewModels()
    lateinit var groupInfo: GroupInfo
    private lateinit var memberAdapter: MemberAdapter
    private lateinit var moimAdapter: MoimAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as GroupActivity
    }

    override fun init() {
        binding.fragment = this
        groupInfo = DMApp.group
        binding.isMaster = groupInfo.masterId == DMApp.user.id
        binding.groupInfo = groupInfo
        initView()
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        groupInfo = DMApp.group
        binding.groupInfo = groupInfo
        viewModel.getMoim(groupInfo.title)
    }

    private fun initView() {
        activity?.let {
            memberAdapter = MemberAdapter(it)
            moimAdapter = MoimAdapter(it)
        }

        binding.recMoim.setEmptyText("현재 예정된 정모가 없습니다.")

        binding.recMember.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        binding.recMoim.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        binding.recMember.adapter = memberAdapter
        binding.recMoim.adapter = moimAdapter
    }

    private fun initViewModel() {
        activity?.groupInfo?.let {
            viewModel.loadMember(it.title)
        }

        viewModel.moimResponse.observe(viewLifecycleOwner) {
            log("getMoim() , observe() , Response => $it")

            when (it.code){
                ResponseCode.SUCCESS -> {
                    val body = it.body.asJsonArray
                    val moim = Gson().fromJson<ArrayList<Moim>>(
                        body, object : TypeToken<ArrayList<Moim>>() {}.type
                    )

                    moimAdapter.updateList(moim)
                }

                else -> {

                }
            }
        }

        viewModel.memberResponse.observe(viewLifecycleOwner) {
            DMLog.e("[GroupFragment] , GroupInfoFragment , getMember() Response => $it")
            when (it.code) {
                ResponseCode.SUCCESS -> {
                    val body = it.body.asJsonArray
                    val gson = Gson()
                    val member = gson.fromJson<ArrayList<User>>(
                        body,
                        object : TypeToken<ArrayList<User>>() {}.type
                    )
                    DMApp.group.member = member
                    memberAdapter.updateList(member)
                    member.forEach { user ->
                        if (user.id == DMApp.user.id) {
                            binding.btnJoin.gone(true)
                            DMApp.group.isMember = true
                            return@forEach
                        }
                    }
                }
                else -> {

                }
            }
        }

        viewModel.joinResponse.observe(viewLifecycleOwner) {

            log("join() , observe() , Response => $it")

            when (it.code) {
                ResponseCode.SUCCESS -> {
                    activity?.showMessage("모임 가입에 성공했습니다.") {
                        viewModel.loadMember(groupInfo.title)
                    }
                }

                ResponseCode.ALREADY_EXIST -> {
                    activity?.showMessage("이미 가입된 모임입니다.") {
                        viewModel.loadMember(groupInfo.title)
                    }
                }

                else -> {
                    activity?.showMessage(String.format("모임 가입 실패 [%s]", "E01 : ${it.code}"))
                }
            }
        }
    }

    fun join(@Suppress("UNUSED_PARAMETER") vw: View) {
        viewModel.join(DMApp.user.id, groupInfo.title)
    }

    fun create(@Suppress("UNUSED_PARAMETER") vw: View) {
        activity?.toMoimActivity()
    }

}

class BoardFragment : BaseFragment<FragmentGroupBoardBinding>(R.layout.fragment_group_board) {

    private var activity: GroupActivity? = null
    private val viewModel: GroupViewModel by viewModels()
    private lateinit var adapter: MemberAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as GroupActivity
    }

    override fun init() {

    }
}

class GalleryFragment : BaseFragment<FragmentGroupGalleryBinding>(R.layout.fragment_group_gallery) {

    private var activity: GroupActivity? = null
    private val viewModel: GroupViewModel by viewModels()
    private lateinit var adapter: MemberAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as GroupActivity
    }


    override fun init() {
    }
}


class ChatFragment : BaseFragment<FragmentGroupChatBinding>(R.layout.fragment_group_chat) {

    private var activity: GroupActivity? = null
    private val viewModel: GroupViewModel by viewModels()
    private lateinit var firebaseDB: FirebaseDatabase
    private lateinit var chatRef: DatabaseReference
    private val chatList: ArrayList<Chat> = ArrayList()
    private lateinit var chat: Chat
    private lateinit var adapter: ChatAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as GroupActivity
    }

    override fun init() {
        binding.isMember = DMApp.group.isMember
        initView()
    }

    @SuppressLint("SimpleDateFormat")
    private fun initView() {
        binding.btnSend.setOnClickListener {
            val time = System.currentTimeMillis()

            val chat = Chat(
                DMApp.user.id,
                DMApp.user.name,
                SimpleDateFormat("MM.dd HH:ss").format(time),
                DMApp.user.thumb,
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
                chat = snapshot.getValue(Chat::class.java)!!
                DMApp.group.member.forEach {
                    if (chat.id == it.id) {
                        chat.thumb = it.thumb
                        chat.name = it.name
                        if (chat.id == DMApp.user.id) {
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