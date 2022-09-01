package com.moondroid.project01_meetingapp.ui.view.activity

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityGroupListBinding
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.ui.view.adapter.GroupListAdapter
import com.moondroid.project01_meetingapp.ui.viewmodel.GroupListViewModel
import com.moondroid.project01_meetingapp.utils.ActivityTy
import com.moondroid.project01_meetingapp.utils.GroupListType
import com.moondroid.project01_meetingapp.utils.IntentParam
import com.moondroid.project01_meetingapp.utils.ResponseCode
import com.moondroid.project01_meetingapp.utils.view.log
import dagger.hilt.android.AndroidEntryPoint

/**
 * 모임 리스트
 *  - 최근 본 모임
 *  - 관심 모임
 **/
@AndroidEntryPoint
class GroupListActivity : BaseActivity<ActivityGroupListBinding>(R.layout.activity_group_list) {
    private lateinit var type: TYPE

    private lateinit var adapter: GroupListAdapter

    private val viewModel: GroupListViewModel by viewModels()

    enum class TYPE {
        FAVORITE,
        RECENT
    }

    override fun init() {
        binding.activity = this
        initView()
        initViewModel()
    }

    /**
     * View initialize
     **/
    private fun initView() {

        setSupportActionBar(binding.toolbar)

        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(true)
        }

        when (intent.getIntExtra(IntentParam.TYPE, 0)) {
            GroupListType.FAVORITE -> {
                type = TYPE.FAVORITE
                title = getString(R.string.title_group_list_favorite)
                binding.recycler.setEmptyText(getString(R.string.alm_favor_group_empty))
            }
            GroupListType.RECENT -> {
                type = TYPE.RECENT
                title = getString(R.string.title_group_list_recent)
                binding.recycler.setEmptyText(getString(R.string.alm_recent_group_empty))
            }
            else -> finish()
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recycler.layoutManager = layoutManager

        adapter = GroupListAdapter(this, object : GroupListAdapter.OnItemClickListener {
            override fun onClick() {
                goToGroupActivity(ActivityTy.GROUP_LIST)
            }
        })

        //binding.recycler.emptyView = binding.emptyMsg
        binding.recycler.adapter = adapter
    }

    /**
     * Observe ViewModel
     **/
    private fun initViewModel() {
        viewModel.showLoading.observe(this) {
            showLoading(it)
        }

        viewModel.showError.observe(this) {
            showNetworkError(it)
        }

        if (type == TYPE.FAVORITE) {
            viewModel.getFavorite(DMApp.user.id)

            viewModel.favoriteResponse.observe(this) {
                log("[GroupListActivity] , favoriteResponse : $it")
                when (it.code) {
                    ResponseCode.SUCCESS -> {
                        val gson = GsonBuilder().create()
                        val newList = gson.fromJson<ArrayList<GroupInfo>>(
                            it.body,
                            object : TypeToken<ArrayList<GroupInfo>>() {}.type
                        )

                        adapter.update(newList)
                    }

                    else -> {
                        showError(
                            String.format(
                                getString(R.string.error_load_group_list_fail),
                                "[E01 : ${it.code}]"
                            )
                        )
                    }
                }
            }
        } else if (type == TYPE.RECENT) {
            viewModel.getRecent(DMApp.user.id)

            viewModel.recentResponse.observe(this) {
                log("[GroupListActivity] , recentResponse : $it")
                when (it.code) {
                    ResponseCode.SUCCESS -> {
                        val gson = GsonBuilder().create()
                        val newList = gson.fromJson<ArrayList<GroupInfo>>(
                            it.body,
                            object : TypeToken<ArrayList<GroupInfo>>() {}.type
                        )

                        adapter.update(newList)
                    }

                    else -> {
                        showError(
                            String.format(
                                getString(R.string.error_load_group_list_fail),
                                "[E02 : ${it.code}]"
                            )
                        ) {
                            finish()
                        }
                    }
                }
            }
        }
    }

}