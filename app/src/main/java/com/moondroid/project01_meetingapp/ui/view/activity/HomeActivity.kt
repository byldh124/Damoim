package com.moondroid.project01_meetingapp.ui.view.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityHomeBinding
import com.moondroid.project01_meetingapp.databinding.LayoutNavigationHeaderBinding
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.ui.view.fragment.GroupListFragment
import com.moondroid.project01_meetingapp.ui.view.fragment.LocationFragment
import com.moondroid.project01_meetingapp.ui.view.fragment.PremiumFragment
import com.moondroid.project01_meetingapp.ui.view.fragment.MyGroupFragment
import com.moondroid.project01_meetingapp.ui.viewmodel.HomeViewModel
import com.moondroid.project01_meetingapp.utils.Constants
import com.moondroid.project01_meetingapp.utils.view.logException
import com.moondroid.project01_meetingapp.utils.view.startActivityWithAnim
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var headerBinding: LayoutNavigationHeaderBinding
    private val viewModel: HomeViewModel by viewModel()
    lateinit var user: User

    lateinit var groupsList: ArrayList<GroupInfo>

    private val getInterest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result?.resultCode == RESULT_OK) {
                    result.data?.let {
                        viewModel.updateInterest(
                            DMApp.user.id,
                            getString(it.getIntExtra(Constants.IntentParam.INTEREST, 0))
                        )
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
            headerBinding = LayoutNavigationHeaderBinding.bind(binding.homeNav.getHeaderView(0))

            initView()
            initViewModel()
            checkPermission()

        } catch (e: Exception) {
            logException(e)
        }
    }

    override fun onResume() {
        super.onResume()
        user = DMApp.user
        binding.homeActivity = this
        headerBinding.homeActivity = this
    }

    /**
     * View 초기화
     */
    private fun initView() {
        try {
            title = getString(R.string.cmn_find_group)

            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)

            val drawerToggle =
                ActionBarDrawerToggle(
                    this,
                    binding.drawer,
                    binding.toolbar,
                    R.string.app_name,
                    R.string.app_name
                )

            drawerToggle.syncState()
            binding.drawer.addDrawerListener(drawerToggle)

            binding.bnv.run {
                setOnItemSelectedListener {
                    when (it.itemId) {
                        R.id.bnv_tab1 -> {
                            changeFragment(GroupListFragment())
                            title = getString(R.string.cmn_find_group)
                        }
                        R.id.bnv_tab2 -> {
                            changeFragment(PremiumFragment())
                            title = getString(R.string.cmn_premium)
                        }
                        R.id.bnv_tab3 -> {
                            changeFragment(MyGroupFragment())
                            title = getString(R.string.cmn_my_group)
                        }
                        R.id.bnv_tab4 -> {
                            changeFragment(LocationFragment())
                            title = getString(R.string.cmn_search_nearly_group)
                        }
                    }
                    binding.homeActivity = this@HomeActivity
                    true
                }
                selectedItemId = R.id.bnv_tab1
            }
            initNavigation()
        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun initViewModel() {
        viewModel.showLoading.observe(this) {
            showLoading(it)
        }

        viewModel.interestResponse.observe(this) {
            when (it.code) {
                Constants.ResponseCode.SUCCESS -> {
                    showError(getString(R.string.alm_update_interest_success))
                }

                Constants.ResponseCode.FAIL -> {
                    showError(getString(R.string.error_update_interest_fail))
                }
            }
        }
    }

    /**
     *  네비게이션 클릭 이벤트 처리
     **/
    private fun initNavigation() {
        try {
            binding.homeNav.itemIconTintList = null

            binding.homeNav.setNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navInterest -> {
                        getInterest.launch(Intent(this@HomeActivity, InterestActivity::class.java))
                    }

                    R.id.navFavorite -> {
                        goToGroupListActivity(Constants.GroupListType.FAVORITE)
                    }

                    R.id.navRecent -> {
                        goToGroupListActivity(Constants.GroupListType.RECENT)
                    }

                    R.id.navSetting -> {
                        val intent = Intent(this, SettingActivity::class.java)
                        intent.putExtra(Constants.ACTIVITY_TY, Constants.ActivityTy.HOME)
                        startActivityWithAnim(intent)
                    }
                }

                binding.drawer.closeDrawer(binding.homeNav)

                true
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 최근 본 목록, 관심 목록 페이지로 전환
     * Type 1 - 최근 본 목록
     * Type 2 - 관심 목록
     * */
    private fun goToGroupListActivity(type: Int) {
        val intent = Intent(this, GroupListActivity::class.java)
        intent.putExtra(Constants.IntentParam.TYPE, type)
        startActivityWithAnim(intent)
    }

    /**
     * Permission 체크
     * WRITE_EXTERNAL_STORAGE
     * ACCESS_FINE_LOCATION
     */
    private fun checkPermission() {
        try {
            val requestPermissionLauncher =
                registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                    //DO NOTHING
                }
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_DENIED
            ) {
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * GROUP 만들기 클릭시 액티비티 전환
     */
    fun goToCreateGroupActivity() {
        try {
            val intent = Intent(this, CreateActivity::class.java)
            intent.putExtra(Constants.ACTIVITY_TY, Constants.ActivityTy.HOME)
            startActivityWithAnim(intent)
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 프로필 세팅 액티비티 전환
     **/
    fun toProfileActivity(vw: View) {
        try {
            val intent = Intent(this, MyInfoActivity::class.java)
            intent.putExtra(Constants.ACTIVITY_TY, Constants.ActivityTy.HOME)
            startActivityWithAnim(intent)
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * BottomNavigationView 클릭시 Fragment 전환
     */
    private fun changeFragment(fragment: Fragment) {
        try {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        } catch (e: Exception) {
            logException(e)
        }
    }
}