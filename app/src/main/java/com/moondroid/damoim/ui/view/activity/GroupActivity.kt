package com.moondroid.damoim.ui.view.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.moondroid.damoim.R
import com.moondroid.damoim.application.DMApp
import com.moondroid.damoim.databinding.ActivityGroupBinding
import com.moondroid.damoim.model.GroupInfo
import com.moondroid.damoim.ui.view.fragment.*
import com.moondroid.damoim.utils.view.logException
import kotlinx.android.synthetic.main.activity_group.*


class GroupActivity : FragmentActivity() {
    private val NUM_PAGES = 4
    private lateinit var binding: ActivityGroupBinding
    lateinit var groupInfo: GroupInfo
    lateinit var title: String
    val fragments = arrayOf(
        InfoFragment(),
        BoardFragment(),
        GalleryFragment(),
        ChatFragment()
    )

    override fun onBackPressed() {
        if (pager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            pager.currentItem = pager.currentItem - 1
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_group)
            binding.groupActivity = this
            groupInfo = DMApp.group

            title = groupInfo.meetName

            initView()
        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun initView() {
        //Action Ba
        val pagerAdapter = PagerAdapter(this)
        pager.adapter = pagerAdapter
    }

    private inner class PagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

    }
}