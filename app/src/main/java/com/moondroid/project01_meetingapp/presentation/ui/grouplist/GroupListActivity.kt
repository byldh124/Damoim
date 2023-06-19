package com.moondroid.project01_meetingapp.presentation.ui.grouplist

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.damoim.common.ActivityTy
import com.moondroid.damoim.common.GroupListType
import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.common.IntentParam
import com.moondroid.damoim.common.Extension.init
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.Extension.repeatOnStarted
import com.moondroid.damoim.common.Extension.startActivityWithAnim
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivityGroupListBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.activity.GroupActivity
import com.moondroid.project01_meetingapp.presentation.ui.grouplist.GroupListViewModel.Event
import dagger.hilt.android.AndroidEntryPoint

/**
 * 모임 리스트
 *  - 최근 본 모임
 *  - 관심 모임
 */
@AndroidEntryPoint
class GroupListActivity : BaseActivity(R.layout.activity_group_list) {
    private val binding by viewBinding(ActivityGroupListBinding::inflate)
    private lateinit var adapter: GroupListAdapter
    private val viewModel: GroupListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repeatOnStarted {
            viewModel.eventFlow.collect {
                handleEvent(it)
            }
        }
        initView()
    }

    /**
     * View initialize
     */
    private fun initView() {
        try {
            binding.toolbar.init(this)

            val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            binding.recycler.layoutManager = layoutManager

            adapter = GroupListAdapter {
                DMApp.group = it
                startActivityWithAnim(Intent(this@GroupListActivity, GroupActivity::class.java))
                goToGroupActivity(ActivityTy.GROUP_LIST)
            }
            binding.recycler.adapter = adapter


            val type: GroupType = when (intent.getIntExtra(IntentParam.TYPE, 0)) {
                GroupListType.FAVORITE -> {
                    GroupType.FAVORITE
                }
                GroupListType.RECENT -> {
                    GroupType.RECENT
                }
                else -> {
                    return
                }
            }

            binding.type = type
            viewModel.getList(type)

        } catch (e: Exception) {
            e.logException()
        }
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.Error -> showMessage(event.code.toString())
            is Event.Loading -> showLoading(event.boolean)
        }
    }
}