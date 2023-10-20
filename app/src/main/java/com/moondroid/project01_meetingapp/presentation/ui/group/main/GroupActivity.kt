package com.moondroid.project01_meetingapp.presentation.ui.group.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.moondroid.damoim.common.IntentParam
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivityGroupBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.dialog.TutorialDialog
import com.moondroid.project01_meetingapp.presentation.ui.group.GroupInfoActivity
import com.moondroid.project01_meetingapp.presentation.ui.group.main.chat.ChatFragment
import com.moondroid.project01_meetingapp.presentation.ui.group.main.detail.GroupDetailFragment
import com.moondroid.project01_meetingapp.presentation.ui.group.main.gallery.GalleryFragment
import com.moondroid.project01_meetingapp.presentation.ui.moim.MoimActivity
import com.moondroid.project01_meetingapp.utils.BindingAdapter.visible
import com.moondroid.project01_meetingapp.utils.ViewExtension.collectEvent
import com.moondroid.project01_meetingapp.utils.ViewExtension.setupToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates


/**
 * 2. 모임 상세 화면
 *  TabLayout - ViewPager
 *   - 모임 상세
 *   - 게시판
 *   - 사진첩
 *   - 채팅
 */
@AndroidEntryPoint
class GroupActivity : BaseActivity() {

    enum class UserType {
        MASTER, MEMBER, VISITOR
    }

    private val pageNum = 3
    private val binding by viewBinding(ActivityGroupBinding::inflate)
    private val viewModel: GroupViewModel by viewModels()
    lateinit var groupInfo: GroupItem

    val fragments = arrayOf(GroupDetailFragment(), GalleryFragment(), ChatFragment())

    var userType by Delegates.observable(UserType.VISITOR) { _, _, type ->
        binding.icSetting.visible(type == UserType.MASTER)
    }

    override fun onBack() {
        if (binding.pager.currentItem == 0) {
            super.onBack()
        } else {
            binding.pager.currentItem = 0
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.model = viewModel

        collectEvent(viewModel.eventFlow, ::handleEvent)

        initView()
    }

    private fun handleEvent(event: GroupViewModel.Event) {
        when (event) {
            is GroupViewModel.Event.NetworkError -> networkError(event.throwable)
            is GroupViewModel.Event.ServerError -> serverError(event.code)
        }
    }

    /**
     * View, Fragment 초기화
     */
    private fun initView() {
        setupToolbar(binding.toolbar)

        binding.tvTitle.text = DMApp.group.title
        val pagerAdapter = PagerAdapter(this)
        binding.pager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.cmn_tab_group_info)
                //1 -> tab.text = getString(R.string.cmn_tab_board)
                1 -> tab.text = getString(R.string.cmn_tab_gallery)
                2 -> tab.text = getString(R.string.cmn_tab_chat)
                else -> tab.text = getString(R.string.cmn_tab_default)
            }
        }.attach()

        if (intent.getBooleanExtra(IntentParam.SHOW_TUTORIAL, false))
            TutorialDialog(this).show()

        binding.icSetting.setOnClickListener {
            val sIntent = Intent(this, GroupInfoActivity::class.java)
            settingLauncher.launch(sIntent)
        }
    }

    private val settingLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            (fragments[0] as GroupDetailFragment).reset()
        }
    }

    private inner class PagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        override fun getItemCount(): Int = pageNum

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.fade_in, 0)
    }

    fun toMoimActivity() {
        val sIntent = Intent(this, MoimActivity::class.java)
        moimLauncher.launch(sIntent)
    }


    private val moimLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            (fragments[0] as GroupDetailFragment).getMoim()
        }
    }
}
