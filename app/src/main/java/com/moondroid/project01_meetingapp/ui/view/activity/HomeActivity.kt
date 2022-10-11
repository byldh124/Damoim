package com.moondroid.project01_meetingapp.ui.view.activity

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityHomeBinding
import com.moondroid.project01_meetingapp.databinding.LayoutNavigationHeaderBinding
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.ui.view.fragment.GroupListFragment
import com.moondroid.project01_meetingapp.ui.view.fragment.LocationFragment
import com.moondroid.project01_meetingapp.ui.view.fragment.MyGroupFragment
import com.moondroid.project01_meetingapp.ui.view.fragment.SearchFragment
import com.moondroid.project01_meetingapp.ui.viewmodel.HomeViewModel
import com.moondroid.project01_meetingapp.utils.ActivityTy
import com.moondroid.project01_meetingapp.utils.GroupListType
import com.moondroid.project01_meetingapp.utils.IntentParam
import com.moondroid.project01_meetingapp.utils.ResponseCode
import com.moondroid.project01_meetingapp.utils.firebase.DMAnalyze
import com.moondroid.project01_meetingapp.utils.view.log
import com.moondroid.project01_meetingapp.utils.view.logException
import com.moondroid.project01_meetingapp.utils.view.startActivityWithAnim
import com.moondroid.project01_meetingapp.utils.view.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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

    private val linkUrl = "https://moondroid.page.link/Zi7X"

    private lateinit var headerBinding: LayoutNavigationHeaderBinding
    private val viewModel: HomeViewModel by viewModels()

    lateinit var groupsList: ArrayList<GroupInfo>

    private var mBackWait = 0L       //Back 2번 클릭

    override fun init() {
        headerBinding = LayoutNavigationHeaderBinding.bind(binding.homeNav.getHeaderView(0))
        initView()
        initViewModel()

        binding.activity = this
        headerBinding.activity = this
        headerBinding.user = user

        checkPermission()
    }

    override fun onBack() {
        if (binding.homeNav.visibility == View.VISIBLE) {
            binding.drawer.closeDrawers()
        } else if (title != getString(R.string.cmn_find_group)) {
            //changeFragment(GroupListFragment())
            binding.bnv.selectedItemId = R.id.bnv_tab1
            title = getString(R.string.cmn_find_group)
        } else if (System.currentTimeMillis() - mBackWait >= 2000) {
            mBackWait = System.currentTimeMillis()
            toast(getString(R.string.alm_two_click_exit))
        } else {
            super.onBack()
        }
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
            val drawerToggle = ActionBarDrawerToggle(
                this, binding.drawer, binding.toolbar, R.string.app_name, R.string.app_name
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
                    binding.activity = this@HomeActivity
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
                        val onResult: (Intent) -> Unit = {
                            viewModel.updateInterest(
                                user!!.id, getString(it.getIntExtra(IntentParam.INTEREST, 0))
                            )
                        }
                        val intent =
                            Intent(this@HomeActivity, InterestActivity::class.java).putExtra(
                                IntentParam.ACTIVITY,
                                ActivityTy.HOME
                            )
                        activityResult(onResult, intent)
                    }

                    R.id.navFavorite -> {
                        goToGroupListActivity(GroupListType.FAVORITE)
                    }

                    R.id.navRecent -> {
                        goToGroupListActivity(GroupListType.RECENT)
                    }

                    /*R.id.navBlock -> {
                        goToGroupListActivity(GroupListType.RECENT)
                    }*/

                    R.id.navSetting -> {
                        val intent = Intent(this, SettingActivity::class.java)
                        intent.putExtra(IntentParam.ACTIVITY, ActivityTy.HOME)
                        startActivityWithAnim(intent)
                    }
                }
                hideNavigation()
                true
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun hideNavigation() {
        binding.drawer.closeDrawer(binding.homeNav)
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
                registerForActivityResult(ActivityResultContracts.RequestPermission()) { //DO NOTHING
                }
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }

            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION
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
    fun toMyInfoActivity(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            val intent = Intent(this, MyInfoActivity::class.java)
            intent.putExtra(IntentParam.ACTIVITY, ActivityTy.HOME)

            val result: (Intent) -> Unit = {
                CoroutineScope(Dispatchers.IO).launch {
                    user = userDao.getUser()[0]
                    headerBinding.user = user
                }
            }
            activityResult(result, intent)
            hideNavigation()
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * BottomNavigationView 클릭시 Fragment 전환
     */
    private fun changeFragment(fragment: Fragment) {
        try {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment)
                .commit()
        } catch (e: Exception) {
            logException(e)
        }
    }

    fun share(@Suppress("UNUSED_PARAMETER") vw: View) {

        DMAnalyze.logEvent("Share Clicked")

        val params = FeedTemplate(
            content = Content(
                title = String.format(
                    "%s %s", getString(R.string.app_sub_name), getString(R.string.app_name)
                ),
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/project01meetingapp.appspot.com/o/logo.png?alt=media&token=4081b1a5-1d77-475b-98cf-63747ba3e37b",
                link = Link(
                    webUrl = linkUrl, mobileWebUrl = linkUrl
                ),
                description = "모임대장에서 다양한 사람들과 새로운 취미를 시작해보세요."
            ), buttons = listOf(
                Button(
                    getString(R.string.cmn_share_button), Link(
                        webUrl = linkUrl, mobileWebUrl = linkUrl
                    )
                )
            )
        )

        // 카카오톡 설치여부 확인
        if (ShareClient.instance.isKakaoTalkSharingAvailable(this)) { // 카카오톡으로 카카오톡 공유 가능
            ShareClient.instance.shareDefault(this, params) { sharingResult, error ->
                if (error != null) {
                    log("카카오톡 공유 실패 : $error")
                } else if (sharingResult != null) {
                    log("카카오톡 공유 성공 ${sharingResult.intent}")
                    startActivity(sharingResult.intent)

                    // 카카오톡 공유에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                    log("Warning Msg: ${sharingResult.warningMsg}")
                    log("Argument Msg: ${sharingResult.argumentMsg}")
                }
            }
        } else { // 카카오톡 미설치: 웹 공유 사용 권장
            // 웹 공유 예시 코드
            val sharerUrl = WebSharerClient.instance.makeDefaultUrl(params)

            // CustomTabs 으로 웹 브라우저 열기

            // 1. CustomTabsServiceConnection 지원 브라우저 열기
            // ex) Chrome, 삼성 인터넷, FireFox, 웨일 등
            try {
                KakaoCustomTabsClient.openWithDefault(this, sharerUrl)
            } catch (e: UnsupportedOperationException) { // CustomTabsServiceConnection 지원 브라우저가 없을 때 예외처리
            }

            // 2. CustomTabsServiceConnection 미지원 브라우저 열기
            // ex) 다음, 네이버 등
            try {
                KakaoCustomTabsClient.open(this, sharerUrl)
            } catch (e: ActivityNotFoundException) { // 디바이스에 설치된 인터넷 브라우저가 없을 때 예외처리
            }
        }
    }

}