package com.moondroid.project01_meetingapp.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityProfileBinding
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.ui.view.adapter.GroupListAdapter
import com.moondroid.project01_meetingapp.ui.viewmodel.ProfileViewModel
import com.moondroid.project01_meetingapp.utils.ActivityTy
import com.moondroid.project01_meetingapp.utils.IntentParam
import com.moondroid.project01_meetingapp.utils.ResponseCode
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>(R.layout.activity_profile),
    GroupListAdapter.OnItemClickListener {
    private val viewModel: ProfileViewModel by viewModels()
    lateinit var user: User
    lateinit var adapter: GroupListAdapter
    override fun init() {
        binding.activity = this
        val userJson = intent.getStringExtra(IntentParam.USER)
        user = Gson().fromJson(userJson, User::class.java)
        binding.user = user

        initView()
        initViewModel()
    }

    private fun initViewModel(){
        viewModel.showLoading.observe(this) {
            showLoading(it)
        }

        viewModel.showError.observe(this) {
            showNetworkError(it)
        }

        viewModel.myGroupsContent.observe(this) {
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

                }
            }
        }

        viewModel.getMyGroup(user.id)
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)

        supportActionBar?.let{
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }

        binding.recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = GroupListAdapter(this, this)
        binding.recycler.adapter = adapter

    }

    override fun onClick() {
        goToGroupActivity(ActivityTy.PROFILE)
    }

}