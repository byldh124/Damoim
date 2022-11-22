package com.moondroid.project01_meetingapp.presentation.view.activity

import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityInterestBinding
import com.moondroid.project01_meetingapp.presentation.view.adapter.InterestAdapter
import com.moondroid.project01_meetingapp.utils.logException
import dagger.hilt.android.AndroidEntryPoint


/**
 * 관심사 선택
 *  -> 선택된 관심사를 Intent Extra 로 전달
 */

@AndroidEntryPoint
class InterestActivity : BaseActivity<ActivityInterestBinding>(R.layout.activity_interest) {

    override fun init() {
        binding.activity = this
        intiView()
    }

    /**
     * Initialize View
     */
    private fun intiView() {
        try {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowTitleEnabled(false)
            }

            val adapter = InterestAdapter(this)
            binding.recycler.adapter = adapter
        } catch (e: Exception) {
            logException(e)
        }
    }
}