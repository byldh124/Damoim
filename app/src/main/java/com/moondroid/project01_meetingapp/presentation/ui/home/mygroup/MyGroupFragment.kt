package com.moondroid.project01_meetingapp.presentation.ui.home.mygroup

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.moondroid.damoim.common.ActivityTy
import com.moondroid.damoim.common.Extension.repeatOnStarted
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.FragmentHomeMyGroupBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseFragment
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.grouplist.GroupListAdapter
import com.moondroid.project01_meetingapp.presentation.ui.home.HomeActivity
import com.moondroid.project01_meetingapp.presentation.ui.home.mygroup.MyGroupViewModel.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyGroupFragment :
    BaseFragment(R.layout.fragment_home_my_group) {
    private val binding by viewBinding(FragmentHomeMyGroupBinding::bind)

    lateinit var activity: HomeActivity
    private val viewModel: MyGroupViewModel by viewModels()
    private lateinit var groupAdapter: GroupListAdapter

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
        viewModel.getMyGroup()
    }

    private fun initView() {
        groupAdapter = GroupListAdapter {
            activity.goToGroupActivity(ActivityTy.HOME)
        }
        binding.recycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recycler.adapter = groupAdapter
        binding.recycler.setEmptyText(getString(R.string.alm_my_group_empty))
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.Update -> groupAdapter.update(event.list)
        }
    }
}