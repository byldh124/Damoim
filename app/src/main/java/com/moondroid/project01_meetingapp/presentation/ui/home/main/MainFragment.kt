package com.moondroid.project01_meetingapp.presentation.ui.home.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.FragmentHomeMainBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseFragment
import com.moondroid.project01_meetingapp.presentation.base.viewModel
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.grouplist.GroupListAdapter
import com.moondroid.project01_meetingapp.presentation.ui.home.HomeActivity
import com.moondroid.project01_meetingapp.presentation.ui.home.main.MainViewModel.Event
import com.moondroid.project01_meetingapp.utils.ViewExtension.collectEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.fragment_home_main) {
    private val binding by viewBinding(FragmentHomeMainBinding::bind)
    private val viewModel: MainViewModel by viewModel()

    lateinit var activity: HomeActivity
    private val groupAdapter = GroupListAdapter {
        DMApp.group = it
        activity.goToGroupActivity()
    }
    private lateinit var categoryAdapter: CategoryListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectEvent(viewModel.eventFlow, ::handleEvent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getGroup()
    }

    private fun initView() {

        binding.recGroup.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        binding.recGroup.adapter = groupAdapter

        categoryAdapter = CategoryListAdapter {
            val list = if (it == "전체") {
                activity.groupsList
            } else {
                activity.groupsList.filter { item -> item.interest == it }
            }
            groupAdapter.submitList(list)
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
            // 카테고리 클릭에 따른 리스트 업데이트
            is Event.Update -> {
                activity.groupsList = event.list
                groupAdapter.submitList(event.list)
            }
        }
    }
}