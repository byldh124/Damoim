package com.moondroid.project01_meetingapp.ui.view.activity

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityProfileBinding
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.ui.view.adapter.GroupListAdapter
import com.moondroid.project01_meetingapp.ui.viewmodel.ProfileViewModel
import com.moondroid.project01_meetingapp.utils.ActivityTy
import com.moondroid.project01_meetingapp.utils.IntentParam
import com.moondroid.project01_meetingapp.utils.ResponseCode
import com.moondroid.project01_meetingapp.utils.view.log
import com.moondroid.project01_meetingapp.utils.view.logException
import com.moondroid.project01_meetingapp.utils.view.startActivityWithAnim
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>(R.layout.activity_profile),
    GroupListAdapter.OnItemClickListener {
    private val viewModel: ProfileViewModel by viewModels()
    lateinit var adapter: GroupListAdapter
    private var profileUser: User? = null

    override fun init() {
        try {
            binding.activity = this
            val userJson = intent.getStringExtra(IntentParam.USER)
            profileUser = Gson().fromJson(userJson, User::class.java)

            if (profileUser == null) finish()

            binding.user = profileUser

            initView()
            initViewModel()

            viewModel.getMyGroup(profileUser!!.id)
        } catch (e: Exception) {
            logException(e)
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
     * Observe ViewModel
     */
    private fun initViewModel() {
        viewModel.showLoading.observe(this) {
            showLoading(it)
        }

        viewModel.showError.observe(this) {
            showNetworkError(it)
        }

        viewModel.myGroupsContent.observe(this) {
            try {
                when (it.code) {
                    ResponseCode.SUCCESS -> {
                        val gson = GsonBuilder().create()
                        val newList = gson.fromJson<ArrayList<GroupInfo>>(
                            it.body, object : TypeToken<ArrayList<GroupInfo>>() {}.type
                        )
                        adapter.update(newList)
                    }

                    else -> {
                        logException(Exception("[ProfileActivity] , getGroupContent()-observe() , Response =>${it.code}"))
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }

        viewModel.blockResponse.observe(this) {
            try {
                when (it.code) {
                    ResponseCode.SUCCESS -> {
                        showMessage("차단이 완료되었습니다.")
                        finish()
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }
    }

    /**
     * Initialize View
     */
    private fun initView() {
        try {
            setSupportActionBar(binding.toolbar)

            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowTitleEnabled(false)
            }

            binding.recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            adapter = GroupListAdapter(this, this)
            binding.recycler.adapter = adapter
        } catch (e: Exception) {
            logException(e)
        }
    }

    override fun onClick() {
        goToGroupActivity(ActivityTy.PROFILE)
    }

    private fun toReportActivity() {
        try {
            val intent = Intent(this, ReportActivity::class.java)
            intent.putExtra(IntentParam.REPORT_ID, profileUser!!.id)
            intent.putExtra(IntentParam.REPORT_NAME, profileUser!!.name)
            startActivityWithAnim(intent)
        } catch (e:Exception) {
            logException(e)
        }
    }
}