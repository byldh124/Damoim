package com.moondroid.project01_meetingapp.presentation.ui.group

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.moondroid.damoim.common.ActivityTy
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.Extension.startActivityWithAnim
import com.moondroid.damoim.common.IntentParam.ACTIVITY
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivityGroupBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.dialog.TutorialDialog
import com.moondroid.project01_meetingapp.presentation.ui.groupinfo.GroupInfoActivity
import com.moondroid.project01_meetingapp.presentation.ui.group.chat.ChatFragment
import com.moondroid.project01_meetingapp.presentation.ui.group.gallery.GalleryFragment
import com.moondroid.project01_meetingapp.presentation.ui.group.main.GroupMainFragment
import com.moondroid.project01_meetingapp.presentation.ui.moim.MoimActivity
import dagger.hilt.android.AndroidEntryPoint


/**
 * 2. 모임 상세 화면
 *  TabLayout - ViewPager
 *   - 모임 상세
 *   - 게시판
 *   - 사진첩
 *   - 채팅
 */
@AndroidEntryPoint
class GroupActivity : BaseActivity(R.layout.activity_group) {
    private val pageNum = 3
    private val binding by viewBinding(ActivityGroupBinding::inflate)
    private val viewModel: GroupViewModel by viewModels()
    lateinit var groupInfo: GroupItem
    lateinit var title: String
    var isFavor = false
    val fragments =
        arrayOf(
            GroupMainFragment(),
            //BoardFragment(),
            GalleryFragment(),
            ChatFragment()
        )


    override fun onBack() {
        if (binding.pager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBack()
        } else {
            // Otherwise, select the previous step.
            binding.pager.currentItem = 0
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        groupInfo = DMApp.group
        initView()
    }

    /**
     * View, Fragment 초기화
     */
    private fun initView() {
        try { //Action Bar
            title = groupInfo.title

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

            if (intent.getIntExtra(ACTIVITY, 0) == ActivityTy.CREATE)
                TutorialDialog(this).show()

            //binding.icSetting.gone(groupInfo.masterId != user!!.id)
        } catch (e: Exception) {
            e.logException()
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

    fun favor() {
        viewModel.saveFavor(groupInfo.title, !isFavor)
    }

    fun toGroupInfo() {
        try {
            val intent = Intent(this, GroupInfoActivity::class.java)
            intent.putExtra(ACTIVITY, ActivityTy.GROUP)
            startActivityWithAnim(intent)
        } catch (e: Exception) {
            e.logException()
        }
    }

    fun toMoimActivity() {
        try {
            val intent = Intent(this, MoimActivity::class.java)
            intent.putExtra(ACTIVITY, ActivityTy.GROUP)
            startActivityWithAnim(intent)
        } catch (e: Exception) {
            e.logException()
        }
    }
}