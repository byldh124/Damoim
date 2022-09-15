package com.moondroid.project01_meetingapp.ui.view.activity

import android.location.Geocoder
import android.os.Build
import androidx.recyclerview.widget.LinearLayoutManager
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityLocationBinding
import com.moondroid.project01_meetingapp.model.Address
import com.moondroid.project01_meetingapp.ui.view.adapter.AddressAdapter
import com.moondroid.project01_meetingapp.ui.view.adapter.LocationAdapter
import com.moondroid.project01_meetingapp.utils.ActivityTy
import com.moondroid.project01_meetingapp.utils.IntentParam
import com.moondroid.project01_meetingapp.utils.view.afterTextChanged
import com.moondroid.project01_meetingapp.utils.view.logException
import com.moondroid.project01_meetingapp.utils.view.visible
import com.naver.maps.geometry.LatLng
import java.util.*


/**
 * 지역 선택
 *  -> 선택된 지역을 Intent Extra 로 전달
 */
class LocationActivity : BaseActivity<ActivityLocationBinding>(R.layout.activity_location) {
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var addressAdapter: AddressAdapter
    private lateinit var geocoder: Geocoder
    private lateinit var type: TYPE
    private val address = ArrayList<Address>()

    enum class TYPE {
        LOCAL,                  // 읍,면,동 검색
        ADDRESS                 // 상세 주소 검색[모임]
    }

    override fun init() {
        binding.activity = this
        geocoder = Geocoder(this, Locale.KOREA)
        initView()
    }

    /**
     * Initialize View
     */
    private fun initView() {
        try {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowTitleEnabled(false)
            }

            val activityType = intent.getIntExtra(IntentParam.ACTIVITY, 0)

            if (activityType == ActivityTy.MOIM) {
                title = getString(R.string.title_address)
                type = TYPE.ADDRESS
            } else {
                title = getString(R.string.title_location_choice)
                type = TYPE.LOCAL
            }
            binding.activity = this

            locationAdapter = LocationAdapter(this)
            addressAdapter = AddressAdapter(this)

            binding.recycler.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            when (type) {
                TYPE.LOCAL -> {
                    binding.recycler.adapter = locationAdapter

                    val locations =
                        resources.getStringArray(R.array.location).toCollection(ArrayList())

                    locationAdapter.updateList(locations)

                    binding.etLocation.afterTextChanged { edit ->
                        binding.recycler.setEmptyText(
                            String.format(
                                getString(R.string.alm_empty_data_for_query),
                                edit
                            )
                        )
                        val newLocation = ArrayList<String>()
                        locations.forEach {
                            if (it.contains(edit)) {
                                newLocation.add(it)
                            }
                        }
                        locationAdapter.updateList(newLocation)
                    }
                }

                TYPE.ADDRESS -> {
                    binding.etLocation.hint = getString(R.string.alm_input_location)
                    binding.icSearch.visible()
                    binding.recycler.adapter = addressAdapter

                    binding.icSearch.setOnClickListener {
                        try {
                            val query = binding.etLocation.text.toString()
                            binding.recycler.setEmptyText(
                                String.format(
                                    getString(R.string.alm_empty_data_for_query),
                                    query
                                )
                            )

                            if (query.isNotEmpty()) {
                                address.clear()
                                if (Build.VERSION.SDK_INT >= 33) {
                                    geocoder.getFromLocationName(query, 10) { list ->
                                        list.forEach {
                                            val target = it.getAddressLine(0).replace("대한민국 ", "")
                                            address.add(
                                                Address(
                                                    "$target [$query]",
                                                    LatLng(it.latitude, it.longitude)
                                                )
                                            )
                                        }
                                    }
                                } else {
                                    @Suppress("DEPRECATION") // deprecated over SDK VERSION 33
                                    val result = geocoder.getFromLocationName(query, 10)
                                    result?.forEach {
                                        val target = it.getAddressLine(0).replace("대한민국 ", "")
                                        address.add(
                                            Address(
                                                "$target [$query]",
                                                LatLng(it.latitude, it.longitude)
                                            )
                                        )
                                    }
                                }
                                addressAdapter.updateList(address)
                            }
                        } catch (e: Exception) {
                            logException(e)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            logException(e)
        }
    }
}