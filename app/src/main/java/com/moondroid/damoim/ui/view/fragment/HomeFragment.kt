package com.moondroid.damoim.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.moondroid.damoim.R
import com.moondroid.damoim.application.DMApp
import com.moondroid.damoim.base.BaseFragment
import com.moondroid.damoim.model.GroupInfo
import com.moondroid.damoim.ui.view.activity.HomeActivity
import com.moondroid.damoim.ui.view.adapter.CategoryListAdapter
import com.moondroid.damoim.ui.view.adapter.GroupListAdapter
import com.moondroid.damoim.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.dialog_loading.*
import kotlinx.android.synthetic.main.fragment_home_group_list.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class GroupListFragment :
    BaseFragment(),
    GroupListAdapter.OnItemClickListener,
    CategoryListAdapter.OnItemClickListener {

    lateinit var activity: HomeActivity
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var groupAdapter: GroupListAdapter
    private lateinit var categoryAdapter: CategoryListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_group_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() {
        groupAdapter = GroupListAdapter(activity, this)

        recGroup.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        recGroup.adapter = groupAdapter

        val categories = resources.getStringArray(R.array.category_for_interest_in_meet)

        categoryAdapter = CategoryListAdapter(
            activity,
            categories.toCollection(ArrayList<String>()),
            this
        )

        recCategory.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        recCategory.adapter = categoryAdapter

    }

    /**
     * ViewModel 초기화
     */
    private fun initViewModel() {
        viewModel.loadGroup()

        viewModel.groupsContent.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                groupAdapter.updateList(it)
            }
        })
    }

    override fun onClick(groupInfo: GroupInfo) {
        DMApp.group = groupInfo
        activity.goToGroupActivity()
    }

    override fun onClick(category: String) {
        groupAdapter.updateList(category)
    }

    fun goToCreateGroupActivity(vw: View) {
        activity.goToCreateGroupActivity()
    }
}

class PremiumFragment : BaseFragment() {

    lateinit var activity: HomeActivity
    private val viewModel: HomeViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_premium, container, false)
    }
}

class SearchFragment : BaseFragment() {

    lateinit var activity: HomeActivity
    private val viewModel: HomeViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_search, container, false)
    }
}

class LocationFragment : BaseFragment() {

    lateinit var activity: HomeActivity
    private val viewModel: HomeViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_location, container, false)
    }
}