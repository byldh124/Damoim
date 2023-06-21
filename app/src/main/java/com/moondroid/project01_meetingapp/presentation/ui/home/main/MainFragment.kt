package com.moondroid.project01_meetingapp.presentation.ui.home.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.moondroid.damoim.common.ActivityTy
import com.moondroid.damoim.common.Extension.repeatOnStarted
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.FragmentHomeMainBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseFragment
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.grouplist.GroupListAdapter
import com.moondroid.project01_meetingapp.presentation.ui.home.HomeActivity
import com.moondroid.project01_meetingapp.presentation.ui.home.main.MainViewModel.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_home_main) {
    private val binding by viewBinding(FragmentHomeMainBinding::bind)
    //private val binding by viewBinding(FragmentHomeMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()

    lateinit var activity: HomeActivity
    private var groupAdapter: GroupListAdapter? = null
    private lateinit var categoryAdapter: CategoryListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repeatOnStarted {
            viewModel.eventFlow.collect {
                handleEvent(it)
            }
        }
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
        groupAdapter = GroupListAdapter {
            DMApp.group = it
            activity.goToGroupActivity(ActivityTy.HOME)
        }

        binding.recGroup.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        binding.recGroup.adapter = groupAdapter

        categoryAdapter = CategoryListAdapter {
            groupAdapter?.updateList(it)
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
                activity.groupsList = event.list
                groupAdapter?.updateList(event.list)
            }
        }
    }
}