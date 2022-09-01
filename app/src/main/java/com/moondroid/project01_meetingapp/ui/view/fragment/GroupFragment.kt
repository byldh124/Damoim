package com.moondroid.project01_meetingapp.ui.view.fragment

import android.content.Context
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseFragment
import com.moondroid.project01_meetingapp.databinding.FragmentGroupBoardBinding
import com.moondroid.project01_meetingapp.databinding.FragmentGroupChatBinding
import com.moondroid.project01_meetingapp.databinding.FragmentGroupGalleryBinding
import com.moondroid.project01_meetingapp.databinding.FragmentGroupInfoBinding
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.ui.view.activity.GroupActivity
import com.moondroid.project01_meetingapp.ui.view.adapter.MemberListAdapter
import com.moondroid.project01_meetingapp.ui.viewmodel.GroupViewModel
import com.moondroid.project01_meetingapp.utils.DMLog
import com.moondroid.project01_meetingapp.utils.ResponseCode
import com.moondroid.project01_meetingapp.utils.view.gone
import com.moondroid.project01_meetingapp.utils.view.log
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : BaseFragment<FragmentGroupInfoBinding>(R.layout.fragment_group_info) {

    private var activity: GroupActivity? = null
    private val viewModel: GroupViewModel by viewModels()
    lateinit var groupInfo: GroupInfo
    private lateinit var adapter: MemberListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as GroupActivity
    }

    override fun init() {
        binding.fragment = this
        groupInfo = DMApp.group
        binding.groupInfo = groupInfo
        initView()
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        groupInfo = DMApp.group
        binding.groupInfo = groupInfo
    }

    private fun initView() {
        adapter = activity?.let {
            MemberListAdapter(it)
        }!!

        binding.recMoim.setEmptyText("현재 예정된 정모가 없습니다.")

        binding.recMember.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recMember.adapter = adapter
    }

    private fun initViewModel() {
        activity?.groupInfo?.let {
            viewModel.loadMember(it.title)
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
                    adapter.updateList(member)
                    member.forEach { user ->
                        if (user.id == DMApp.user.id) {
                            binding.btnJoin.gone(true)
                            return@forEach
                        }
                    }
                }
                else -> {

                }
            }
        }

        viewModel.joinResponse.observe(viewLifecycleOwner) {

            log("[GroupActivity] , join() , observe() , Response => $it")

            when (it.code) {
                ResponseCode.SUCCESS -> {
                    activity?.showError("모임 가입에 성공했습니다.") {
                        viewModel.loadMember(groupInfo.title)
                    }
                }

                ResponseCode.ALREADY_EXIST -> {
                    activity?.showError("이미 가입된 모임입니다.") {
                        viewModel.loadMember(groupInfo.title)
                    }
                }

                else -> {
                    activity?.showError(String.format("모임 가입 실패 [%s]", "E01 : ${it.code}"))
                }
            }
        }
    }

    fun join(@Suppress("UNUSED_PARAMETER") vw: View) {
        viewModel.join(DMApp.user.id, groupInfo.title)
    }

}

class BoardFragment : BaseFragment<FragmentGroupBoardBinding>(R.layout.fragment_group_board) {

    private var activity: GroupActivity? = null
    private val viewModel: GroupViewModel by viewModels()
    private lateinit var adapter: MemberListAdapter

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
    private lateinit var adapter: MemberListAdapter

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
    private lateinit var adapter: MemberListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as GroupActivity
    }

    override fun init() {
    }
}