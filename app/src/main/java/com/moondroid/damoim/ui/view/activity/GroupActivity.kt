package com.moondroid.damoim.ui.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.gson.Gson
import com.moondroid.damoim.R
import com.moondroid.damoim.base.BaseActivity
import com.moondroid.damoim.databinding.ActivityGroupBinding
import com.moondroid.damoim.model.GroupInfo
import com.moondroid.damoim.ui.view.fragment.GroupInfoFragment
import com.moondroid.damoim.utils.Constants
import com.moondroid.damoim.utils.DMLog
import com.moondroid.damoim.utils.view.logException
import kotlinx.android.synthetic.main.activity_group.*


class GroupActivity : FragmentActivity() {
    private val NUM_PAGES = 1
    private lateinit var binding: ActivityGroupBinding
    lateinit var groupInfo: GroupInfo
    lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_group)
            binding.groupActivity = this

            title = "모임정보"
            DMLog.e("GroupActivity is Start")

            val groupInfo: String? = intent.getStringExtra(Constants.currentGroup)
            DMLog.e(groupInfo.toString())

            this.groupInfo = Gson().fromJson(groupInfo, GroupInfo::class.java)

            initView()
        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun initView() {
        val pagerAdapter = PagerAdapter(this)
        pager.adapter = pagerAdapter
    }

    private inner class PagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = GroupInfoFragment()

    }
}