package com.moondroid.project01_meetingapp.presentation.ui.group.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.moondroid.damoim.common.Extension.debug
import com.moondroid.damoim.common.IntentParam
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.FragmentGroupInfoBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseFragment
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.group.GroupActivity
import com.moondroid.project01_meetingapp.presentation.ui.moim.MoimInfoActivity
import com.moondroid.project01_meetingapp.presentation.ui.profile.ProfileActivity
import com.moondroid.project01_meetingapp.utils.ViewExtension.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupDetailFragment : BaseFragment(R.layout.fragment_group_info) {

    private val viewModel: GroupDetailViewModel by viewModels()
    private lateinit var activity: GroupActivity
    private val binding by viewBinding(FragmentGroupInfoBinding::bind)

    lateinit var groupInfo: GroupItem
    private lateinit var memberAdapter: MemberAdapter
    private lateinit var moimListAdapter: MoimListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as GroupActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        groupInfo = DMApp.group
        binding.groupInfo = groupInfo
        initView()

        repeatOnStarted {
            viewModel.eventFlow.collect {
                handleEvent(it)
            }
        }

        viewModel.loadMember(groupInfo.title)
        viewModel.getMoim(groupInfo.title)
    }

    private fun handleEvent(event: GroupDetailViewModel.Event) {
        when (event) {
            is GroupDetailViewModel.Event.Members -> {
                debug("members: ${event.list}")
                memberAdapter.updateList(event.list)
            }
            is GroupDetailViewModel.Event.Moims -> moimListAdapter.updateList(event.list)
            is GroupDetailViewModel.Event.NetworkError -> activity.networkError(event.throwable)
            is GroupDetailViewModel.Event.ServerError -> activity.serverError(event.code)
            GroupDetailViewModel.Event.Join -> activity.showMessage("가입을 완료했습니다") {
                viewModel.loadMember(groupInfo.title)
            }
        }
    }

    private fun initView() {
        activity.let {
            memberAdapter = MemberAdapter {
                val intent = Intent(activity, ProfileActivity::class.java)
                    .apply {
                        putExtra(IntentParam.USER, Gson().toJson(it))
                        addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    }
                activity.startActivity(intent)
            }
            moimListAdapter = MoimListAdapter {
                val sIntent = Intent(activity, MoimInfoActivity::class.java)
                    .putExtra(IntentParam.MOIM, Gson().toJson(it))
                activity.startActivity(sIntent)
            }
        }

        binding.btnJoin.setOnClickListener {
            viewModel.join(groupInfo.title)
        }

        binding.recMoim.setEmptyText("현재 예정된 정모가 없습니다.")

        binding.recMember.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        binding.recMoim.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        binding.recMember.adapter = memberAdapter
        binding.recMoim.adapter = moimListAdapter
    }

    fun create() {
        activity.toMoimActivity()
    }

}