package com.moondroid.project01_meetingapp.presentation.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.moondroid.project01_meetingapp.presentation.base.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.moondroid.damoim.common.IntentParam
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivityProfileBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.grouplist.GroupListAdapter
import com.moondroid.project01_meetingapp.utils.ViewExtension.collectEvent
import com.moondroid.project01_meetingapp.utils.ViewExtension.setupToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity() {
    private val binding by viewBinding(ActivityProfileBinding::inflate)
    private val viewModel: ProfileViewModel by viewModel()
    private val adapter = GroupListAdapter {
        DMApp.group = it
        goToGroupActivity()
    }
    private var profileUser: Profile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userJson = intent.getStringExtra(IntentParam.USER)
        profileUser = Gson().fromJson(userJson, Profile::class.java)

        if (profileUser == null) finish()

        binding.user = profileUser

        collectEvent(viewModel.eventFlow, ::handleEvent)

        initView()

        viewModel.getMyGroup(profileUser!!.id)
    }

    private fun handleEvent(event: ProfileViewModel.Event) {
        when (event) {
            is ProfileViewModel.Event.UpdateGroup -> adapter.submitList(event.list)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            /*R.id.menuBlock -> {
                showMessage2("해당 유저를 차단하시겠습니까?\n차단된 사용자는 설정 - 차단목록에서 해제하실 수 있습니다.") {
                    viewModel.blockUser(user!!.id, profileUser!!.id)
                }
            }*/

            R.id.menuReport -> {
                toReportActivity()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Initialize View
     */
    private fun initView() {
        setupToolbar(binding.toolbar)

        binding.recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.recycler.adapter = adapter
    }

    private fun toReportActivity() {
        val intent = Intent(this, ReportActivity::class.java)
        intent.putExtra(IntentParam.REPORT_ID, profileUser!!.id)
        intent.putExtra(IntentParam.REPORT_NAME, profileUser!!.name)
        startActivity(intent)
    }
}