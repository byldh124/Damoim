package com.moondroid.project01_meetingapp.presentation.ui.moim

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.moondroid.project01_meetingapp.utils.ViewExtension.init
import com.moondroid.damoim.common.Extension.logException

import com.moondroid.damoim.common.IntentParam
import com.moondroid.damoim.domain.model.MoimItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.moim.JoinMoimUseCase
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.databinding.ActivityMoimInfoBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.profile.ProfileActivity
import com.moondroid.project01_meetingapp.presentation.ui.group.main.detail.MemberAdapter
import com.moondroid.project01_meetingapp.utils.BindingAdapter.visible
import com.moondroid.project01_meetingapp.utils.ViewExtension.enable
import com.moondroid.project01_meetingapp.utils.ViewExtension.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MoimInfoActivity : BaseActivity() {
    private val binding by viewBinding(ActivityMoimInfoBinding::inflate)
    private val viewModel: MoimInfoViewModel by viewModels()

    private val adapter = MemberAdapter {
        toProfile(it)
    }
    private lateinit var moim: MoimItem
    private var isMember = false

    @Inject
    lateinit var joinMoimUseCase: JoinMoimUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isMember = intent.getBooleanExtra(IntentParam.USER_IS_MEMBER_THIS_GROUP, false)

        moim = Gson().fromJson(intent.getStringExtra(IntentParam.MOIM), MoimItem::class.java)
        binding.moim = moim

        repeatOnStarted {
            viewModel.eventFlow.collect {
                handleEvent(it)
            }
        }

        initView()

        viewModel.getMember(moim.joinMember)
    }

    private fun handleEvent(event: MoimInfoViewModel.Event) {
        when (event) {
            is MoimInfoViewModel.Event.Error -> networkError(event.throwable)
            is MoimInfoViewModel.Event.Fail -> serverError(event.code)
            is MoimInfoViewModel.Event.Loading -> showLoading(event.loading)
            is MoimInfoViewModel.Event.Members -> {
                event.list.also {
                    binding.btnJoin.visible(!it.contains(DMApp.profile))
                    adapter.updateList(it)
                }
            }
        }
    }

    private fun initView() {
        binding.toolbar.init(this)

        binding.recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.recycler.adapter = adapter

        binding.btnJoin.enable(isMember)

        binding.btnJoin.setOnClickListener { join() }
    }

    private fun toProfile(profile: Profile) {
        val intent = Intent(this, ProfileActivity::class.java).apply {
            putExtra(IntentParam.USER, Gson().toJson(profile))
            addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(intent)
    }

    private fun join() {
        CoroutineScope(Dispatchers.Main).launch {
            showLoading(true)
            joinMoimUseCase(DMApp.profile.id, moim.title, moim.date).collect { result ->
                showLoading(false)
                result.onSuccess {
                    moim = it
                    viewModel.getMember(moim.joinMember)
                }.onFail {
                    serverError(it)
                }.onError {
                    networkError(it)
                }
            }
        }
    }
}