package com.moondroid.project01_meetingapp.ui.view.activity

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.databinding.ActivityGroupBinding
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.ui.view.dialog.TutorialDialog
import com.moondroid.project01_meetingapp.ui.view.fragment.*
import com.moondroid.project01_meetingapp.utils.Constants
import com.moondroid.project01_meetingapp.utils.view.gone
import com.moondroid.project01_meetingapp.utils.view.logException
import com.moondroid.project01_meetingapp.utils.view.visible


class GroupActivity : FragmentActivity() {
    private val NUM_PAGES = 4
    private lateinit var binding: ActivityGroupBinding
    private lateinit var animation: Animation
    lateinit var groupInfo: GroupInfo
    lateinit var title: String
    val fragments = arrayOf(
        InfoFragment(),
        BoardFragment(),
        GalleryFragment(),
        ChatFragment()
    )

    override fun onBackPressed() {
        if (binding.pager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            binding.pager.currentItem = binding.pager.currentItem - 1
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_group)

            groupInfo = DMApp.group

            binding.groupActivity = this
            initView()
        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun initView() {
        try {
            //Action Bar
            title = groupInfo.title

            val pagerAdapter = PagerAdapter(this)
            binding.pager.adapter = pagerAdapter

            TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.cmn_tab_group_info)
                    1 -> tab.text = getString(R.string.cmn_tab_board)
                    2 -> tab.text = getString(R.string.cmn_tab_gallery)
                    3 -> tab.text = getString(R.string.cmn_tab_chat)
                    else -> tab.text = getString(R.string.cmn_tab_default)
                }
            }.attach()

            if (intent.getIntExtra(Constants.ACTIVITY_TY, 0) ==Constants.ActivityTy.CREATE)
                TutorialDialog(this).show()

            binding.icSetting.gone(groupInfo.masterId != DMApp.user.id)

        } catch (e: Exception) {
            logException(e)
        }
    }

    private inner class PagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.fade_in, 0)
    }


    fun join(@Suppress("UNUSED_PARAMETER")vw: View){

    }
}