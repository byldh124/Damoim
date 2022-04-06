package com.moondroid.damoim.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.moondroid.damoim.R
import com.moondroid.damoim.base.BaseActivity
import com.moondroid.damoim.databinding.ActivityHomeBinding
import com.moondroid.damoim.model.GroupInfo
import com.moondroid.damoim.ui.view.adapter.GroupListAdapter
import com.moondroid.damoim.ui.viewmodel.HomeViewModel
import com.moondroid.damoim.utils.Constants
import com.moondroid.damoim.utils.view.logException
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.dialog_loading.*
import org.koin.android.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity(), GroupListAdapter.OnItemClickListener {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var adapter: GroupListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
            binding.homeActivity = this

            initview()

            initViewModel()
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * View 초기화
     */
    private fun initview() {
        title = getString(R.string.home_find_group)

        adapter = GroupListAdapter(this, this)
        recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = adapter
    }

    /**
     * ViewModel 초기화
     */
    private fun initViewModel() {
        viewModel.loadGroup()

        viewModel.groupsContent.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter.updateList(it)
            }
        })

        viewModel.showLoading.observe(this, Observer { showLoading ->
            ltLoading.visibility = if (showLoading!!) View.VISIBLE else View.GONE
        })
    }

    override fun onClick(groupInfo: GroupInfo) {
        val newIntent = Intent(this, GroupActivity::class.java)
        newIntent.putExtra(
            Constants.ACTIVITY_TY,
            Constants.ActivityTy.HOME
        )

        newIntent.putExtra(
            Constants.currentGroup,
            Gson().toJson(groupInfo)
        )
        startActivity(newIntent)
        overridePendingTransition(android.R.anim.fade_in, 0)
    }

}