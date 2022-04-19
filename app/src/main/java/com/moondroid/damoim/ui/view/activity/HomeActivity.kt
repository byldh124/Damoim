package com.moondroid.damoim.ui.view.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.moondroid.damoim.R
import com.moondroid.damoim.application.DMApp
import com.moondroid.damoim.base.BaseActivity
import com.moondroid.damoim.databinding.ActivityHomeBinding
import com.moondroid.damoim.databinding.LayoutNavigationHeaderBinding
import com.moondroid.damoim.model.User
import com.moondroid.damoim.ui.view.fragment.GroupListFragment
import com.moondroid.damoim.ui.view.fragment.LocationFragment
import com.moondroid.damoim.ui.view.fragment.PremiumFragment
import com.moondroid.damoim.ui.view.fragment.MyGroupFragment
import com.moondroid.damoim.utils.Constants
import com.moondroid.damoim.utils.view.logException
import com.moondroid.damoim.utils.view.startActivityWithAnim
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var headerBinding: LayoutNavigationHeaderBinding
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
            headerBinding = LayoutNavigationHeaderBinding.bind(binding.homeNav.getHeaderView(0))

            user = DMApp.user

            binding.homeActivity = this
            headerBinding.homeActivity = this

            initView()
            checkPermission()

        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * View 초기화
     */
    private fun initView() {
        title = getString(R.string.home_find_group)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val drawerToggle =
            ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name)

        drawerToggle.syncState()
        drawer.addDrawerListener(drawerToggle)

        bnv.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.bnv_tab1 -> {
                        changeFragment(GroupListFragment())
                        title = getString(R.string.home_find_group)
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
    }

    private fun initNavigation() {
        try {
            homeNav.itemIconTintList = null
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * Permission 체크
     * WRITE_EXTERNAL_STORAGE
     * ACCESS_FINE_LOCATION
     */
    private fun checkPermission() {
        try {
            val requestPermissionLauncher =
                registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
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
     * GROUP_ITEM 클릭시 액티비티 전환
     */
    fun goToGroupActivity() {
        try {
            val newIntent = Intent(this, GroupActivity::class.java)
            newIntent.putExtra(
                Constants.ACTIVITY_TY,
                Constants.ActivityTy.HOME
            )
            startActivityWithAnim(newIntent)

        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * GROUP 만들기 클릭시 액티비티 전환
     */
    fun goToCreateGroupActivity() {
        try {
            val intent = Intent(this, CreateGroupActivity::class.java)
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