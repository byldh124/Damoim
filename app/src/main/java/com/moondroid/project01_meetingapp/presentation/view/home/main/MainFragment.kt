package com.moondroid.project01_meetingapp.presentation.view.home.main

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseFragment
import com.moondroid.project01_meetingapp.databinding.FragmentHomeMainBinding
import com.moondroid.project01_meetingapp.domain.model.GroupInfo
import com.moondroid.project01_meetingapp.presentation.view.grouplist.GroupListAdapter
import com.moondroid.project01_meetingapp.presentation.view.home.HomeActivity
import com.moondroid.project01_meetingapp.presentation.view.home.main.MainViewModel.Event
import com.moondroid.project01_meetingapp.utils.ActivityTy
import com.moondroid.project01_meetingapp.utils.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment :
    BaseFragment<FragmentHomeMainBinding>(R.layout.fragment_home_main) {

    lateinit var activity: HomeActivity
    private val viewModel: MainViewModel by viewModels()
    private lateinit var groupAdapter: GroupListAdapter
    private lateinit var categoryAdapter: CategoryListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun init() {
        initView()
        repeatOnStarted {
            viewModel.eventFlow.collect {
                handleEvent(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getGroup()
    }

    private fun initView() {
        groupAdapter = GroupListAdapter {
            DMApp.group = it
            activity.goToGroupActivity(ActivityTy.HOME)
        }

        binding.recGroup.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        binding.recGroup.adapter = groupAdapter

        categoryAdapter = CategoryListAdapter {
            groupAdapter.updateList(it)
            binding.recGroup.setEmptyText(String.format(getString(R.string.alm_empty_data_for_query), it))
        }

        binding.recCategory.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        binding.recCategory.adapter = categoryAdapter

        binding.btnAddMeet.setOnClickListener {
            activity.goToCreateGroupActivity()
        }

    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.Update -> {
                activity.groupsList = event.list as ArrayList<GroupInfo>
                groupAdapter.updateList(event.list)
            }
        }
    }
}