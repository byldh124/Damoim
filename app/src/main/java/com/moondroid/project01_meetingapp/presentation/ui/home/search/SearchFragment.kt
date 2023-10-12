package com.moondroid.project01_meetingapp.presentation.ui.home.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.FragmentHomeSearchBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseFragment
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.grouplist.GroupListAdapter
import com.moondroid.project01_meetingapp.presentation.ui.home.HomeActivity
import com.moondroid.project01_meetingapp.utils.ViewExtension.afterTextChanged

class SearchFragment : BaseFragment(R.layout.fragment_home_search) {
    private val binding by viewBinding(FragmentHomeSearchBinding::bind)

    lateinit var activity: HomeActivity
    private val groups = ArrayList<GroupItem>()
    private lateinit var groupAdapter: GroupListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        try {
            groupAdapter = GroupListAdapter {
                activity.goToGroupActivity()
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