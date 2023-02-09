package com.moondroid.project01_meetingapp.presentation.view.home.search

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseFragment
import com.moondroid.project01_meetingapp.databinding.FragmentHomeSearchBinding
import com.moondroid.project01_meetingapp.domain.model.GroupInfo
import com.moondroid.project01_meetingapp.presentation.view.grouplist.GroupListAdapter
import com.moondroid.project01_meetingapp.presentation.view.home.HomeActivity
import com.moondroid.project01_meetingapp.utils.ActivityTy
import com.moondroid.project01_meetingapp.utils.afterTextChanged
import com.moondroid.project01_meetingapp.utils.logException

class SearchFragment : BaseFragment<FragmentHomeSearchBinding>(R.layout.fragment_home_search) {
    lateinit var activity: HomeActivity
    private val groups = ArrayList<GroupInfo>()
    private lateinit var groupAdapter: GroupListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun init() {
        initView()
    }

    private fun initView() {
        try {
            groupAdapter = GroupListAdapter {
                activity.goToGroupActivity(ActivityTy.HOME)
            }
            binding.recycler.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            binding.recycler.adapter = groupAdapter

            binding.etQuery.afterTextChanged { query ->
                groups.clear()
                binding.recycler.setEmptyText(String.format(getString(R.string.alm_search_data_empty), query))
                activity.groupsList.forEach {
                    if (query.isEmpty()) return@forEach
                    if (
                        it.title.contains(query) ||
                        it.purpose.contains(query) ||
                        it.information.contains(query) ||
                        it.interest.contains(query)
                    ) {
                        groups.add(it)
                    }
                }

                groupAdapter.update(groups)
            }

        } catch (e: Exception) {
            logException(e)
        }
    }
}