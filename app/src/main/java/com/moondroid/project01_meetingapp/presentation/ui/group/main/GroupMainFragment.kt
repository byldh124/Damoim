package com.moondroid.project01_meetingapp.presentation.ui.group.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.moondroid.damoim.common.ActivityTy
import com.moondroid.damoim.common.Extension.startActivityWithAnim
import com.moondroid.damoim.common.IntentParam
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.FragmentGroupInfoBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseFragment
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.moim.MoimInfoActivity
import com.moondroid.project01_meetingapp.presentation.ui.profile.ProfileActivity
import com.moondroid.project01_meetingapp.presentation.ui.group.GroupActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupMainFragment : BaseFragment(R.layout.fragment_group_info) {

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
                activity.startActivityWithAnim(intent)
            }
            moimListAdapter = MoimListAdapter {
                activity.startActivityWithAnim(
                    Intent(activity, MoimInfoActivity::class.java)
                        .putExtra(IntentParam.MOIM, Gson().toJson(it))
                        .putExtra(IntentParam.ACTIVITY, ActivityTy.GROUP)
                )
            }
        }

        binding.recMoim.setEmptyText("현재 예정된 정모가 없습니다.")

        binding.recMember.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        binding.recMoim.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        binding.recMember.adapter = memberAdapter
        binding.recMoim.adapter = moimListAdapter
    }

    fun create(){
        activity.toMoimActivity()
    }

}