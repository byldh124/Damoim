package com.moondroid.project01_meetingapp.presentation.view.activity

import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityMoimInfoBinding
import com.moondroid.project01_meetingapp.domain.model.DMUser
import com.moondroid.project01_meetingapp.domain.model.Moim
import com.moondroid.project01_meetingapp.presentation.view.adapter.MemberAdapter
import com.moondroid.project01_meetingapp.presentation.viewmodel.MoimInfoViewModel
import com.moondroid.project01_meetingapp.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoimInfoActivity : BaseActivity<ActivityMoimInfoBinding>(R.layout.activity_moim_info) {
    private val viewModel: MoimInfoViewModel by viewModels()
    private lateinit var adapter: MemberAdapter
    private lateinit var moim: Moim

    override fun init() {
        try {
            binding.activity = this
            moim = Gson().fromJson(intent.getStringExtra(IntentParam.MOIM), Moim::class.java)
            binding.moim = moim

            initView()
            initViewModel()

            viewModel.getMember(moim.joinMember)
        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun initView() {
        try {
            binding.toolbar.init(this)

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
                        val member = gson.fromJson<ArrayList<DMUser>>(
                            body, object : TypeToken<ArrayList<DMUser>>() {}.type
                        )
                        adapter.updateList(member)
                        member.forEach { item ->
                            if (user!!.id == item.id) {
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
        try {
            viewModel.join(user!!.id, moim.title, moim.date)
        } catch (e: Exception) {
            logException(e)
        }
    }
}