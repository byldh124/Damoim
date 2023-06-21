package com.moondroid.project01_meetingapp.presentation.ui.group.moim

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.moondroid.damoim.common.Extension.init
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.Extension.startActivityWithAnim
import com.moondroid.damoim.common.IntentParam
import com.moondroid.damoim.domain.model.MoimItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivityMoimInfoBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.profile.ProfileActivity
import com.moondroid.project01_meetingapp.presentation.ui.group.main.MemberAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoimInfoActivity : BaseActivity(R.layout.activity_moim_info) {
    private val binding by viewBinding(ActivityMoimInfoBinding::inflate)
    private val viewModel: MoimInfoViewModel by viewModels()

    private lateinit var adapter: MemberAdapter
    private lateinit var moim: MoimItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        moim = Gson().fromJson(intent.getStringExtra(IntentParam.MOIM), MoimItem::class.java)
        binding.moim = moim

        initView()

        viewModel.getMember(moim.joinMember)
    }

    private fun initView() {
        try {
            binding.toolbar.init(this)

            binding.recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            adapter = MemberAdapter {
                toProfile(it)
            }
            binding.recycler.adapter = adapter

            binding.btnJoin.setOnClickListener { join() }
        } catch (e: Exception) {
            e.logException()
        }
    }

    private fun toProfile(profile: Profile) {
        val intent = Intent(this, ProfileActivity::class.java).apply {
            putExtra(IntentParam.USER, Gson().toJson(profile))
            addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        this@MoimInfoActivity.startActivityWithAnim(intent)
    }

    private fun join() {
        viewModel.join(moim.title, moim.date)
    }
}