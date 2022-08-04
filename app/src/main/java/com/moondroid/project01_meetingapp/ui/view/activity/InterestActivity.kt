package com.moondroid.project01_meetingapp.ui.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityInterestBinding
import com.moondroid.project01_meetingapp.ui.view.adapter.InterestAdapter

class InterestActivity : BaseActivity() {
    private lateinit var binding: ActivityInterestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_interest)
        binding.activity = this

        intiView()
    }

    private fun intiView(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }

        val adapter = InterestAdapter(this)
        binding.recycler.adapter = adapter

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}