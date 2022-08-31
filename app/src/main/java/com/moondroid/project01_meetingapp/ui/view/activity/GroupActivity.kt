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
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityGroupBinding
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.ui.view.dialog.TutorialDialog
import com.moondroid.project01_meetingapp.ui.view.fragment.*
import com.moondroid.project01_meetingapp.ui.viewmodel.GroupViewModel
import com.moondroid.project01_meetingapp.utils.ActivityTy
import com.moondroid.project01_meetingapp.utils.IntentParam
import com.moondroid.project01_meetingapp.utils.ResponseCode
import com.moondroid.project01_meetingapp.utils.view.gone
import com.moondroid.project01_meetingapp.utils.view.log
import com.moondroid.project01_meetingapp.utils.view.logException
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class GroupActivity : BaseActivity<ActivityGroupBinding>(R.layout.activity_group) {
    private val pageNum = 4
    private val viewModel: GroupViewModel by viewModels()
    lateinit var groupInfo: GroupInfo
    lateinit var title: String
    var isFavor = false
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
            binding.pager.currentItem = 0
        }
    }

    override fun init() {
        binding.activity = this
        groupInfo = DMApp.group
        initView()
        initViewModel()
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
        viewModel.showLoading.observe(this) {
            showLoading(it)
        }

        viewModel.showError.observe(this) {
            showNetworkError(it)
        }

        viewModel.saveRecent(DMApp.user.id, groupInfo.title, System.currentTimeMillis().toString())

        viewModel.recentResponse.observe(this) {
            log("[GroupActivity] , saveRecent , observe() , Response => $it")
        }

        viewModel.getFavor(DMApp.user.id, groupInfo.title)

        viewModel.favorResponse.observe(this) {

            log("[GroupActivity] , getFavor() , observe() , Response => $it")

            isFavor = when (it.code) {
                ResponseCode.SUCCESS -> {
                    val body = it.body.asJsonObject
                    body.get("favor").asBoolean
                }
                else -> {
                    false
                }
            }
            binding.activity = this@GroupActivity
        }

        viewModel.saveFavorResponse.observe(this) {

            log("[GroupActivity] , saveFavor() , observe() , Response => $it")

            when (it.code) {
                ResponseCode.SUCCESS -> {
                    val message: String
                    if (!isFavor) {
                        message = "관심목록에 추가되었습니다."
                        isFavor = true
                    } else {
                        message = "관심목록에서 삭제했습니다."
                        isFavor = false
                    }

                    showError(message) {
                        binding.activity = this@GroupActivity
                    }
                }

                else -> {
                    showError(String.format("관심목록 변경에 실패했습니다. [%s]", "E01 : ${it.code}"))
                }
            }

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

    fun favor(@Suppress("UNUSED_PARAMETER") vw: View) {
        viewModel.saveFavor(DMApp.user.id, groupInfo.title, !isFavor)
    }
}