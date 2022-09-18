package com.moondroid.project01_meetingapp.ui.view.activity

import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityMoimInfoBinding
import com.moondroid.project01_meetingapp.model.Moim
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.ui.view.adapter.MemberAdapter
import com.moondroid.project01_meetingapp.ui.viewmodel.MoimInfoViewModel
import com.moondroid.project01_meetingapp.utils.IntentParam
import com.moondroid.project01_meetingapp.utils.ResponseCode
import com.moondroid.project01_meetingapp.utils.view.gone
import com.moondroid.project01_meetingapp.utils.view.log
import com.moondroid.project01_meetingapp.utils.view.logException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoimInfoActivity : BaseActivity<ActivityMoimInfoBinding>(R.layout.activity_moim_info) {
    private val viewModel: MoimInfoViewModel by viewModels()
    private lateinit var adapter: MemberAdapter
    private lateinit var moim: Moim

    override fun init() {
        binding.activity = this
        moim = Gson().fromJson(intent.getStringExtra(IntentParam.MOIM), Moim::class.java)
        binding.moim = moim

        initView()
        initViewModel()

        viewModel.getMember(moim.joinMember)
    }

    private fun initView() {
        try {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.let {
                it.setDisplayShowTitleEnabled(false)
                it.setDisplayHomeAsUpEnabled(true)
            }

            binding.recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            adapter = MemberAdapter(this)
            binding.recycler.adapter = adapter
        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun initViewModel() {
        viewModel.showLoading.observe(this) {
            showLoading(it)
        }

        viewModel.showError.observe(this) {
            showNetworkError(it)
        }

        viewModel.memberResponse.observe(this) {
            log("getMember() , observe() Response => $it")
            try {
                when (it.code) {
                    ResponseCode.SUCCESS -> {
                        val body = it.body.asJsonArray
                        val gson = Gson()
                        val member = gson.fromJson<ArrayList<User>>(
                            body,
                            object : TypeToken<ArrayList<User>>() {}.type
                        )
                        adapter.updateList(member)
                        member.forEach { user ->
                            if (user.id == DMApp.user.id) {
                                binding.btnJoin.gone(true)
                                return@forEach
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }

        }

        viewModel.joinResponse.observe(this) {
            log("joinMoim() , observe() , Response => $it")
            try {
                when (it.code) {
                    ResponseCode.SUCCESS -> {
                        val body = it.body.asJsonObject
                        val gson = Gson()
                        moim = gson.fromJson(body, object : TypeToken<Moim>() {}.type)
                        viewModel.getMember(moim.joinMember)
                        binding.moim = moim
                    }

                    else -> {
                        showMessage(getString(R.string.error_join_moim_fail), "E01 : ${it.code}")
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }
    }

    fun join(@Suppress("UNUSED_PARAMETER") vw: View) {
        viewModel.join(DMApp.user.id, moim.title, moim.date)
    }
}