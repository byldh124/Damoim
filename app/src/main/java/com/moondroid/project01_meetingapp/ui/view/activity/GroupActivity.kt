package com.moondroid.project01_meetingapp.ui.view.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
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
import com.moondroid.project01_meetingapp.ui.viewmodel.GroupViewModel
import com.moondroid.project01_meetingapp.utils.ActivityTy
import com.moondroid.project01_meetingapp.utils.IntentParam
import com.moondroid.project01_meetingapp.utils.view.gone
import com.moondroid.project01_meetingapp.utils.view.logException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupActivity : FragmentActivity() {
    private val pageNum = 4
    private lateinit var binding: ActivityGroupBinding
    private val viewModel : GroupViewModel by viewModels()
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
            initViewModel()
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

            if (intent.getIntExtra(IntentParam.ACTIVITY, 0) == ActivityTy.CREATE)
                TutorialDialog(this).show()

            binding.icSetting.gone(groupInfo.masterId != DMApp.user.id)

        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun initViewModel() {

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


    fun join(@Suppress("UNUSED_PARAMETER") vw: View) {

    }
}