package com.moondroid.project01_meetingapp.presentation.view.activity

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityGroupBinding
import com.moondroid.project01_meetingapp.domain.model.GroupInfo
import com.moondroid.project01_meetingapp.presentation.dialog.TutorialDialog
import com.moondroid.project01_meetingapp.presentation.view.fragment.ChatFragment
import com.moondroid.project01_meetingapp.presentation.view.fragment.GalleryFragment
import com.moondroid.project01_meetingapp.presentation.view.fragment.InfoFragment
import com.moondroid.project01_meetingapp.presentation.viewmodel.GroupViewModel
import com.moondroid.project01_meetingapp.utils.ActivityTy
import com.moondroid.project01_meetingapp.utils.IntentParam.ACTIVITY
import com.moondroid.project01_meetingapp.utils.RequestParam
import com.moondroid.project01_meetingapp.utils.ResponseCode
import com.moondroid.project01_meetingapp.utils.gone
import com.moondroid.project01_meetingapp.utils.log
import com.moondroid.project01_meetingapp.utils.logException
import com.moondroid.project01_meetingapp.utils.startActivityWithAnim
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
class GroupActivity : BaseActivity<ActivityGroupBinding>(R.layout.activity_group) {
    private val pageNum = 3
    private val viewModel: GroupViewModel by viewModels()
    lateinit var groupInfo: GroupInfo
    lateinit var title: String
    var isFavor = false
    val fragments =
        arrayOf(
            InfoFragment(),
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

    override fun init() {
        binding.activity = this
        groupInfo = DMApp.group
        initView()
        initViewModel()
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

            binding.icSetting.gone(groupInfo.masterId != user!!.id)
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * Observe ViewModel
     */
    private fun initViewModel() {
        viewModel.showLoading.observe(this) {
            showLoading(it)
        }

        viewModel.showError.observe(this) {
            showNetworkError(it)
        }

        viewModel.saveRecent(user!!.id, groupInfo.title, System.currentTimeMillis().toString())

        viewModel.recentResponse.observe(this) {
            log("saveRecent , observe() , Response => $it")
        }

        viewModel.getFavor(user!!.id, groupInfo.title)

        viewModel.favorResponse.observe(this) {
            log("getFavor() , observe() , Response => $it")
            try {
                isFavor = when (it.code) {
                    ResponseCode.SUCCESS -> {
                        val body = it.body.asJsonObject
                        body.get(RequestParam.FAVOR).asBoolean
                    }
                    else -> {
                        false
                    }
                }
                binding.activity = this@GroupActivity
            } catch (e: Exception) {
                logException(e)
            }
        }

        viewModel.saveFavorResponse.observe(this) {
            log("saveFavor() , observe() , Response => $it")
            try {
                when (it.code) {
                    ResponseCode.SUCCESS -> {
                        val message: String
                        if (!isFavor) {
                            message = getString(R.string.alm_favorite_add)
                            isFavor = true
                        } else {
                            message = getString(R.string.alm_delete_favorite)
                            isFavor = false
                        }

                        showMessage(message) {
                            binding.activity = this@GroupActivity
                        }
                    }

                    else -> {
                        showMessage(getString(R.string.error_change_favorite_fail), "E01 : ${it.code}")
                    }
                }
            } catch (e: Exception) {
                logException(e)
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
        viewModel.saveFavor(user!!.id, groupInfo.title, !isFavor)
    }

    fun toGroupInfo(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            val intent = Intent(this, GroupInfoActivity::class.java)
            intent.putExtra(ACTIVITY, ActivityTy.GROUP)
            startActivityWithAnim(intent)
        } catch (e: Exception) {
            logException(e)
        }
    }

    fun toMoimActivity() {
        try {
            val intent = Intent(this, MoimActivity::class.java)
            intent.putExtra(ACTIVITY, ActivityTy.GROUP)
            startActivityWithAnim(intent)
        } catch (e: Exception) {
            logException(e)
        }
    }
}