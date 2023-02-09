package com.moondroid.project01_meetingapp.presentation.view.home.mygroup

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseFragment
import com.moondroid.project01_meetingapp.databinding.FragmentHomeMyGroupBinding
import com.moondroid.project01_meetingapp.domain.model.GroupInfo
import com.moondroid.project01_meetingapp.presentation.view.grouplist.GroupListAdapter
import com.moondroid.project01_meetingapp.presentation.view.home.HomeActivity
import com.moondroid.project01_meetingapp.presentation.view.home.HomeViewModel
import com.moondroid.project01_meetingapp.presentation.view.home.mygroup.MyGroupViewModel.Event
import com.moondroid.project01_meetingapp.presentation.viewmodel.GroupViewModel
import com.moondroid.project01_meetingapp.utils.ActivityTy
import com.moondroid.project01_meetingapp.utils.ResponseCode
import com.moondroid.project01_meetingapp.utils.log
import com.moondroid.project01_meetingapp.utils.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class MyGroupFragment :
    BaseFragment<FragmentHomeMyGroupBinding>(R.layout.fragment_home_my_group) {

    lateinit var activity: HomeActivity
    private val viewModel: MyGroupViewModel by viewModels()
    private lateinit var groupAdapter: GroupListAdapter

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