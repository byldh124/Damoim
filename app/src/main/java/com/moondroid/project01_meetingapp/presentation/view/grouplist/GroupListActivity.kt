package com.moondroid.project01_meetingapp.presentation.view.grouplist

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityGroupListBinding
import com.moondroid.project01_meetingapp.domain.model.GroupInfo
import com.moondroid.project01_meetingapp.presentation.view.grouplist.GroupListViewModel.Event
import com.moondroid.project01_meetingapp.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * 모임 리스트
 *  - 최근 본 모임
 *  - 관심 모임
 */
@AndroidEntryPoint
class GroupListActivity : BaseActivity<ActivityGroupListBinding>(R.layout.activity_group_list) {
    private lateinit var adapter: GroupListAdapter
    private val viewModel: GroupListViewModel by viewModels()

    override fun init() {
        initView()
        repeatOnStarted {
            viewModel.eventFlow.collect {
                handleEvent(it)
            }
        }
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
            logException(e)
        }
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.Error -> showMessage(event.code.toString())
            is Event.Loading -> showLoading(event.boolean)
        }
    }
}