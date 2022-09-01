package com.moondroid.project01_meetingapp.ui.view.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityLocationBinding
import com.moondroid.project01_meetingapp.ui.view.adapter.LocationAdapter
import com.moondroid.project01_meetingapp.utils.view.afterTextChanged
import com.moondroid.project01_meetingapp.utils.view.logException
import kotlin.collections.ArrayList


/**
 * 지역 선택
 *  -> 선택된 지역을 Intent Extra 로 전달
 **/
class LocationActivity : BaseActivity<ActivityLocationBinding>(R.layout.activity_location) {
    private lateinit var adapter: LocationAdapter

    override fun init() {
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