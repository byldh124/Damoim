package com.moondroid.project01_meetingapp.presentation.view.location

import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityLocationBinding
import com.moondroid.project01_meetingapp.domain.model.Address
import com.moondroid.project01_meetingapp.utils.ActivityTy
import com.moondroid.project01_meetingapp.utils.IntentParam
import com.moondroid.project01_meetingapp.utils.afterTextChanged
import com.moondroid.project01_meetingapp.utils.logException
import com.moondroid.project01_meetingapp.utils.visible
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


/**
 * 지역 선택
 *  -> 선택된 지역을 Intent Extra 로 전달
 */

@AndroidEntryPoint
class LocationActivity : BaseActivity<ActivityLocationBinding>(R.layout.activity_location) {
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var addressAdapter: AddressAdapter
    private lateinit var geocoder: Geocoder
    private lateinit var type: TYPE
    private val address = ArrayList<Address>()
    val title = MutableLiveData<String>()

    enum class TYPE {
        LOCAL,                  // 읍,면,동 검색
        ADDRESS                 // 상세 주소 검색[모임]
    }

    override fun init() {
        geocoder = Geocoder(this, Locale.KOREA)
        initView()
    }

    private fun replace(old: String): String {
        return old.replace("대한민국", "")
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
                binding.txtTitle.text = getString(R.string.title_address)
                type = TYPE.ADDRESS
            } else {
                binding.txtTitle.text = getString(R.string.title_location_choice)
                type = TYPE.LOCAL
            }

            locationAdapter = LocationAdapter {
                val intent = Intent()
                intent.putExtra(IntentParam.LOCATION, it)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

            addressAdapter = AddressAdapter {
                val intent = Intent().putExtra(IntentParam.ADDRESS, Gson().toJson(it))
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

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
                                getString(R.string.alm_empty_data_for_query), edit
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
                                    getString(R.string.alm_empty_data_for_query), query
                                )
                            )

                            if (query.isNotEmpty()) {
                                address.clear()
                                if (Build.VERSION.SDK_INT >= 33) {
                                    geocoder.getFromLocationName(query, 10) { list ->
                                        list.forEach {
                                            val target = replace(it.getAddressLine(0))
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
                                        val target = replace(it.getAddressLine(0))
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