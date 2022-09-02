package com.moondroid.project01_meetingapp.ui.view.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
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
import com.moondroid.project01_meetingapp.ui.view.fragment.SearchFragment
import com.moondroid.project01_meetingapp.ui.view.fragment.MyGroupFragment
import com.moondroid.project01_meetingapp.ui.viewmodel.HomeViewModel
import com.moondroid.project01_meetingapp.utils.ActivityTy
import com.moondroid.project01_meetingapp.utils.GroupListType
import com.moondroid.project01_meetingapp.utils.IntentParam
import com.moondroid.project01_meetingapp.utils.ResponseCode
import com.moondroid.project01_meetingapp.utils.view.logException
import com.moondroid.project01_meetingapp.utils.view.startActivityWithAnim
import com.moondroid.project01_meetingapp.utils.view.toast
import dagger.hilt.android.AndroidEntryPoint


/**
 * 메인 화면 (HOME)
 * 1. Bottom Navigation
 *  - 모임 리스트
 *  - 내 모임 찾기
 *  - 모임 검색
 *  - 현재 정모가 있는 화면(맵)
 *
 * 2. Navigation
 *  - 프로필 수정
 *  - 관심 모임 확인
 *  - 최근 본 모임 확인
 *  - 설정
 *
 * 3. OptionMenu
 *  - 카카오 공유
 */
@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private lateinit var headerBinding: LayoutNavigationHeaderBinding
    private val viewModel: HomeViewModel by viewModels()
    lateinit var user: User

    lateinit var groupsList: ArrayList<GroupInfo>

    private var mBackWait = 0L       //Back 2번 클릭

    /*관심사 ActivityResult*/
    private val getInterest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result?.resultCode == RESULT_OK) {
                    result.data?.let {
                        viewModel.updateInterest(
                            DMApp.user.id,
                            getString(it.getIntExtra(IntentParam.INTEREST, 0))
                        )
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }

    override fun init() {
        headerBinding = LayoutNavigationHeaderBinding.bind(binding.homeNav.getHeaderView(0))
        initView()
        initViewModel()
        checkPermission()
    }

    override fun onBackPressed() {
        if (binding.homeNav.visibility == View.VISIBLE){
            binding.drawer.closeDrawers()
        } else if (title != getString(R.string.cmn_find_group)) {
            //changeFragment(GroupListFragment())
            binding.bnv.selectedItemId = R.id.bnv_tab1
            title = getString(R.string.cmn_find_group)
        } else if(System.currentTimeMillis() - mBackWait >=2000 ) {
            mBackWait = System.currentTimeMillis()
            toast(getString(R.string.alm_two_click_exit))
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        user = DMApp.user
        binding.homeActivity = this
        headerBinding.homeActivity = this
    }

    /**
     * View, Fragment 초기화
     */
    private fun initView() {
        try {
            title = getString(R.string.cmn_find_group)

            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)

            // connect drawer - navigation
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

            //initialize bottom navigation
            binding.bnv.run {
                setOnItemSelectedListener {
                    when (it.itemId) {
                        R.id.bnv_tab1 -> {
                            changeFragment(GroupListFragment())
                            title = getString(R.string.cmn_find_group)
                        }
                        R.id.bnv_tab2 -> {
                            changeFragment(MyGroupFragment())
                            title = getString(R.string.cmn_my_group)
                        }
                        R.id.bnv_tab3 -> {
                            changeFragment(SearchFragment())
                            title = getString(R.string.cmn_search)
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

        viewModel.interestResponse.observe(this) {
            when (it.code) {
                ResponseCode.SUCCESS -> {
                    showMessage(getString(R.string.alm_update_interest_success))
                }

                ResponseCode.FAIL -> {
                    showMessage(getString(R.string.error_update_interest_fail))
                }
            }
        }
    }

    /**
     *  네비게이션 클릭 이벤트 처리
     */
    private fun initNavigation() {
        try {
            binding.homeNav.itemIconTintList = null

            binding.homeNav.setNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navInterest -> {
                        getInterest.launch(Intent(this@HomeActivity, InterestActivity::class.java))
                    }

                    R.id.navFavorite -> {
                        goToGroupListActivity(GroupListType.FAVORITE)
                    }

                    R.id.navRecent -> {
                        goToGroupListActivity(GroupListType.RECENT)
                    }

                    R.id.navSetting -> {
                        val intent = Intent(this, SettingActivity::class.java)
                        intent.putExtra(IntentParam.ACTIVITY, ActivityTy.HOME)
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
        intent.putExtra(IntentParam.TYPE, type)
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
            intent.putExtra(IntentParam.ACTIVITY, ActivityTy.HOME)
            startActivityWithAnim(intent)
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 프로필 세팅 액티비티 전환
     */
    fun toProfileActivity(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            val intent = Intent(this, MyInfoActivity::class.java)
            intent.putExtra(IntentParam.ACTIVITY, ActivityTy.HOME)
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