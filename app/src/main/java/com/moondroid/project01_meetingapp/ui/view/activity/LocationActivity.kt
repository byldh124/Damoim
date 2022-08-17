package com.moondroid.project01_meetingapp.ui.view.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityLocationBinding
import com.moondroid.project01_meetingapp.ui.view.adapter.LocationAdapter
import com.moondroid.project01_meetingapp.utils.view.afterTextChanged
import com.moondroid.project01_meetingapp.utils.view.logException
import kotlin.collections.ArrayList

class LocationActivity : BaseActivity() {
    private lateinit var binding: ActivityLocationBinding
    private lateinit var adapter: LocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_location)
        binding.activity = this


        initView()
    }

    private fun initView() {
        try {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowTitleEnabled(false)
            }

            adapter = LocationAdapter(this)

            binding.recycler.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            binding.recycler.adapter = adapter

            val locations = resources.getStringArray(R.array.location).toCollection(ArrayList())

            adapter.updateList(locations)

            binding.etLocation.afterTextChanged { edit ->
                val newLocation = ArrayList<String>()
                locations.forEach {
                    if (it.contains(edit)) {
                        newLocation.add(it)
                    }
                }
                adapter.updateList(newLocation)
            }
        } catch (e: Exception) {
            logException(e)
        }
    }
}