package com.moondroid.project01_meetingapp.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityGroupListBinding
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.ui.view.adapter.GroupListAdapter
import com.moondroid.project01_meetingapp.ui.viewmodel.GroupListViewModel
import com.moondroid.project01_meetingapp.utils.Constants
import com.moondroid.project01_meetingapp.utils.view.gone
import com.moondroid.project01_meetingapp.utils.view.log
import com.moondroid.project01_meetingapp.utils.view.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class GroupListActivity : BaseActivity() {
    private lateinit var binding: ActivityGroupListBinding

    private lateinit var type: TYPE

    private lateinit var adapter: GroupListAdapter

    private val viewModel: GroupListViewModel by viewModel()

    enum class TYPE {
        FAVORITE,
        RECENT
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_list)

        when (intent.getIntExtra(Constants.IntentParam.TYPE, 0)) {
            Constants.GroupListType.FAVORITE -> {
                type = TYPE.FAVORITE
                title = getString(R.string.title_group_list_favorite)
            }
            Constants.GroupListType.RECENT -> {
                type = TYPE.RECENT
                title = getString(R.string.title_group_list_recent)
            }
            else -> finish()
        }

        binding.activity = this

        initView()
        initViewModel()

    }

    private fun initView() {

        setSupportActionBar(binding.toolbar)

        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(true)
        }
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recycler.layoutManager = layoutManager

        adapter = GroupListAdapter(this, object : GroupListAdapter.OnItemClickListener {
            override fun onClick() {
                goToGroupActivity(Constants.ActivityTy.GROUP_LIST)
            }
        })

        //binding.recycler.emptyView = binding.emptyMsg
        binding.recycler.adapter = adapter
    }

    private fun initViewModel() {
        viewModel.showLoading.observe(this) {
            showLoading(it)
        }

        if (type == TYPE.FAVORITE) {
            viewModel.getFavorite(DMApp.user.id)

            viewModel.favoriteResponse.observe(this) {
                log("[GroupListActivity] , favoriteResponse : $it")
                when (it.code) {
                    Constants.ResponseCode.SUCCESS -> {
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
                    Constants.ResponseCode.SUCCESS -> {
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