package com.moondroid.project01_meetingapp.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseFragment
import com.moondroid.project01_meetingapp.databinding.FragmentHomeGroupListBinding
import com.moondroid.project01_meetingapp.databinding.FragmentHomeMyGroupBinding
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.ui.view.activity.HomeActivity
import com.moondroid.project01_meetingapp.ui.view.adapter.CategoryListAdapter
import com.moondroid.project01_meetingapp.ui.view.adapter.GroupListAdapter
import com.moondroid.project01_meetingapp.ui.viewmodel.HomeViewModel
import com.moondroid.project01_meetingapp.utils.Constants
import com.moondroid.project01_meetingapp.utils.DMLog
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class GroupListFragment :
    BaseFragment(),
    GroupListAdapter.OnItemClickListener,
    CategoryListAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeGroupListBinding
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
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_group_list, container, false)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() {
        groupAdapter = GroupListAdapter(activity, this)

        binding.recGroup.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        binding.recGroup.adapter = groupAdapter

        categoryAdapter = CategoryListAdapter(
            activity,
            this
        )

        binding.recCategory.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        binding.recCategory.adapter = categoryAdapter

    }

    /**
     * ViewModel 초기화
     */
    private fun initViewModel() {
        viewModel.loadGroup()

        viewModel.showLoading.observe(viewLifecycleOwner) {
            activity.showLoading(it)
        }

        viewModel.groupsContent.observe(viewLifecycleOwner) {
            when (it.code) {
                Constants.ResponseCode.SUCCESS -> {
                    val result = it.body.asJsonArray
                    val gson = GsonBuilder().create()
                    val groups = gson.fromJson<ArrayList<GroupInfo>>(
                        result,
                        object : TypeToken<ArrayList<GroupInfo>>() {}.type
                    )

                    groupAdapter.updateList(groups)
                }
            }
        }
    }

    override fun onClick() {
        activity.goToGroupActivity(Constants.ActivityTy.HOME)
    }

    override fun onClick(category: String) {
        groupAdapter.updateList(category)
    }

    fun goToCreateGroupActivity(@Suppress("UNUSED_PARAMETER") vw: View) {
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

class MyGroupFragment :
    BaseFragment(),
    GroupListAdapter.OnItemClickListener {

    lateinit var binding: FragmentHomeMyGroupBinding
    lateinit var activity: HomeActivity
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var groupAdapter: GroupListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_my_group, container, false)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMyGroup(DMApp.user.id)
    }

    private fun initView() {
        groupAdapter = GroupListAdapter(activity, this)
        binding.recycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recycler.adapter = groupAdapter
    }

    private fun initViewModel() {
        viewModel.showLoading.observe(viewLifecycleOwner) {
            activity.showLoading(it)
        }

        viewModel.myGroupsContent.observe(viewLifecycleOwner) {
            DMLog.e("[HomeFragment] , MyGroupFragment , getMyGroup() Response => $it")
            when (it.code) {
                Constants.ResponseCode.SUCCESS -> {
                    val gson = GsonBuilder().create()
                    val newList = gson.fromJson<ArrayList<GroupInfo>>(
                        it.body,
                        object : TypeToken<ArrayList<GroupInfo>>() {}.type
                    )

                    groupAdapter.update(newList)
                }

                else -> {
                    activity.showError(
                        String.format(
                            getString(R.string.error_load_group_list_fail),
                            "[E01 : ${it.code}]"
                        )
                    )
                }
            }
        }
    }

    override fun onClick() {
        activity.goToGroupActivity(Constants.ActivityTy.HOME)
    }
}

class LocationFragment : BaseFragment() {

    lateinit var activity: HomeActivity

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