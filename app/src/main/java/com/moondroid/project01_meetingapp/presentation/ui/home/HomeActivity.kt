package com.moondroid.project01_meetingapp.presentation.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
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
import com.moondroid.damoim.common.Extension.debug
import com.moondroid.damoim.common.GroupListType
import com.moondroid.damoim.common.IntentParam
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivityHomeBinding
import com.moondroid.project01_meetingapp.databinding.LayoutNavigationHeaderBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.base.viewModel
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.common.interest.InterestActivity
import com.moondroid.project01_meetingapp.presentation.ui.group.CreateGroupActivity
import com.moondroid.project01_meetingapp.presentation.ui.grouplist.GroupListActivity
import com.moondroid.project01_meetingapp.presentation.ui.home.HomeViewModel.Event
import com.moondroid.project01_meetingapp.presentation.ui.home.main.MainFragment
import com.moondroid.project01_meetingapp.presentation.ui.home.map.LocationFragment
import com.moondroid.project01_meetingapp.presentation.ui.home.mygroup.MyGroupFragment
import com.moondroid.project01_meetingapp.presentation.ui.home.search.SearchFragment
import com.moondroid.project01_meetingapp.presentation.ui.profile.MyInfoActivity
import com.moondroid.project01_meetingapp.presentation.ui.setting.SettingActivity
import com.moondroid.project01_meetingapp.utils.ProfileHelper
import com.moondroid.project01_meetingapp.utils.ViewExtension.collectEvent
import com.moondroid.project01_meetingapp.utils.ViewExtension.setupToolbar
import com.moondroid.project01_meetingapp.utils.ViewExtension.snack
import com.moondroid.project01_meetingapp.utils.firebase.FBAnalyze
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
class HomeActivity : BaseActivity() {
    private val linkUrl = "https://moondroid.page.link/Zi7X"
    private val binding by viewBinding(ActivityHomeBinding::inflate)

    private lateinit var headerBinding: LayoutNavigationHeaderBinding
    private val viewModel: HomeViewModel by viewModel()

    var groupsList: List<GroupItem> = emptyList()

    private var mBackWait = 0L       //Back 2번 클릭

    private var name = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        headerBinding = LayoutNavigationHeaderBinding.bind(binding.homeNav.getHeaderView(0))
        binding.model = viewModel
        headerBinding.model = viewModel
        headerBinding.profile = ProfileHelper.profile

        collectEvent(viewModel.eventFlow, ::handleEvent)

        initView()

        checkPermission()
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.UpdateInterest -> showMessage(getString(R.string.alm_update_interest_success))
            is Event.SetProfile -> headerBinding.profile = event.profile

            Event.MyProfile -> {
                hideNavigation()
                startActivityForResult(Intent(mContext, MyInfoActivity::class.java)) {
                    viewModel.getUser()
                }
            }
        }
    }

    /**
     * Custom backPressed()
     */
    override fun onBack() {
        if (binding.homeNav.visibility == View.VISIBLE) {
            binding.drawer.closeDrawers()
        } else if (binding.bnv.selectedItemId != R.id.bnv_tab1) {
            binding.bnv.selectedItemId = R.id.bnv_tab1
        } else if (System.currentTimeMillis() - mBackWait >= 2000) {
            mBackWait = System.currentTimeMillis()
            binding.root.snack(getString(R.string.alm_two_click_exit))
        } else {
            super.onBack()
        }
    }

    /**
     * View, Fragment 초기화
     */
    private fun initView() {
        name = getString(R.string.cmn_find_group)

        setupToolbar(binding.toolbar)

        binding.icShare.setOnClickListener { share() }

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
                        changeFragment(MainFragment())
                        name = getString(R.string.cmn_find_group)
                    }

                    R.id.bnv_tab2 -> {
                        changeFragment(MyGroupFragment())
                        name = getString(R.string.cmn_my_group)
                    }

                    R.id.bnv_tab3 -> {
                        changeFragment(SearchFragment())
                        name = getString(R.string.cmn_search)
                    }

                    R.id.bnv_tab4 -> {
                        changeFragment(LocationFragment())
                        name = getString(R.string.cmn_search_nearly_group)
                    }
                }
                binding.name = name
                true
            }
            selectedItemId = R.id.bnv_tab1
        }
        initNavigation()
    }


    /**
     *  네비게이션 클릭 이벤트 처리
     */
    private fun initNavigation() {
        binding.homeNav.itemIconTintList = null

        binding.homeNav.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navInterest -> updateInterest()
                R.id.navFavorite -> goToGroupListActivity(GroupListType.FAVORITE)
                R.id.navRecent -> goToGroupListActivity(GroupListType.RECENT)
                R.id.navSetting -> startActivity(Intent(mContext, SettingActivity::class.java))
            }
            hideNavigation()
            true
        }
    }

    private fun updateInterest() {
        startActivityForResult(Intent(mContext, InterestActivity::class.java)) {
            it?.let {
                viewModel.updateInterest(getString(it.getIntExtra(IntentParam.INTEREST, 0)))
            }
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
        val intent = Intent(mContext, GroupListActivity::class.java)
        intent.putExtra(IntentParam.TYPE, type)
        startActivity(intent)
    }

    /**
     * Permission 체크
     * WRITE_EXTERNAL_STORAGE
     * ACCESS_FINE_LOCATION
     */
    private fun checkPermission() {
        val requestPermissionLauncher =
            registerForActivityResult(RequestMultiplePermissions()) { permissions -> //DO NOTHING
                val denied = permissions.filter { !it.value }
                if (denied.isNotEmpty()) {
                    showMessage("현재 적용하지 않은 권한은 추후에 변경이 가능합니다.")
                }
            }
        val list = ArrayList<String>()
        list.add(Manifest.permission.ACCESS_FINE_LOCATION)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            list.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            list.add(Manifest.permission.READ_MEDIA_IMAGES)
            list.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        val deniedList =
            list.filter {
                ActivityCompat.checkSelfPermission(
                    mContext,
                    it
                ) == PackageManager.PERMISSION_DENIED
            }

        requestPermissionLauncher.launch(deniedList.toTypedArray())
    }

    /**
     * GROUP 만들기 클릭시 액티비티 전환
     */
    fun goToCreateGroupActivity() {
        startActivity(Intent(mContext, CreateGroupActivity::class.java))
    }

    /**
     * BottomNavigationView 클릭시 Fragment 전환
     */
    @SuppressLint("CommitTransaction")
    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    /**
     * 카카오 공유
     **/
    private fun share() {
        FBAnalyze.logEvent("Share Clicked")

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
                    showMessage(getString(R.string.error_share_kakao))
                } else if (sharingResult != null) {
                    startActivity(sharingResult.intent)

                    // 카카오톡 공유에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                    debug("Warning Msg: ${sharingResult.warningMsg}")
                    debug("Argument Msg: ${sharingResult.argumentMsg}")
                }
            }
        } else {
            // 카카오톡 미설치: 웹 공유 사용 권장
            val sharerUrl = WebSharerClient.instance.makeDefaultUrl(params)

            // 1. CustomTabsServiceConnection 지원 브라우저 열기
            // ex) Chrome, 삼성 인터넷, FireFox, 웨일 등
            try {
                KakaoCustomTabsClient.openWithDefault(this, sharerUrl)
            } catch (e: UnsupportedOperationException) {
                // CustomTabsServiceConnection 지원 브라우저가 없을 때 예외처리
            }

            // 2. CustomTabsServiceConnection 미지원 브라우저 열기
            // ex) 다음, 네이버 등
            try {
                KakaoCustomTabsClient.open(this, sharerUrl)
            } catch (e: ActivityNotFoundException) {
                // 디바이스에 설치된 인터넷 브라우저가 없을 때 예외처리
            }
        }
    }
}