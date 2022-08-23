package com.moondroid.project01_meetingapp.ui.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityInterestBinding
import com.moondroid.project01_meetingapp.ui.view.adapter.InterestAdapter
import com.moondroid.project01_meetingapp.utils.view.logException

class InterestActivity : BaseActivity<ActivityInterestBinding>(R.layout.activity_interest) {

    override fun init() {
        binding.activity = this
        intiView()
    }

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