package com.moondroid.damoim.ui.view.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.moondroid.damoim.R
import com.moondroid.damoim.base.BaseActivity
import com.moondroid.damoim.databinding.ActivityHomeBinding
import com.moondroid.damoim.ui.view.fragment.GroupListFragment
import com.moondroid.damoim.ui.view.fragment.LocationFragment
import com.moondroid.damoim.ui.view.fragment.PremiumFragment
import com.moondroid.damoim.ui.view.fragment.SearchFragment
import com.moondroid.damoim.utils.Constants
import com.moondroid.damoim.utils.view.logException
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
            binding.homeActivity = this

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
                    R.id.bnv_tab1 ->
                        changeFragment(GroupListFragment())
                    R.id.bnv_tab2 ->
                        changeFragment(PremiumFragment())
                    R.id.bnv_tab3 ->
                        changeFragment(SearchFragment())
                    R.id.bnv_tab4 ->
                        changeFragment(LocationFragment())
                }
                true
            }
            selectedItemId = R.id.bnv_tab1
        }
    }

    /**
     * Permission 체크
     * WRITE_EXTERNAL_STORAGE
     * ACCESS_FINE_LOCATION
     **/
    private fun checkPermission() {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    showNavigation()
                } else {
                }
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
    }

    private fun showNavigation() {
        if (!homeNav.isShown) {
            drawer.openDrawer(GravityCompat.START)
        }
    }


    /**
     * GROUP_ITEM 클릭시 액티비티 전환
     */
    fun goToGroupActivity() {
        val newIntent = Intent(this, GroupActivity::class.java)
        newIntent.putExtra(
            Constants.ACTIVITY_TY,
            Constants.ActivityTy.HOME
        )
        startActivity(newIntent)
        overridePendingTransition(android.R.anim.fade_in, 0)
    }

    /**
     * GROUP 만들기 클릭시 액티비티 전환
     */
    fun goToCreateGroupActivity() {
        val intent = Intent(this, CreateGroupActivity::class.java)
        intent.putExtra(Constants.ACTIVITY_TY, Constants.ActivityTy.HOME)
        startActivity(intent)
    }

    /**
     * BottomNavigationView 클릭시 Fragment 전환
     */
    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

}