package com.moondroid.project01_meetingapp.presentation.ui.common.location

import android.content.Intent
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.moondroid.damoim.common.IntentParam
import com.moondroid.damoim.domain.model.MoimAddress
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivityLocationBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.utils.ViewExtension.afterTextChanged
import com.moondroid.project01_meetingapp.utils.ViewExtension.visible
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

/**
 * 지역 선택
 *  -> 선택된 지역을 Intent Extra 로 전달
 */

@AndroidEntryPoint
class LocationActivity : BaseActivity() {
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var addressAdapter: AddressAdapter
    private lateinit var geocoder: Geocoder
    private val address = ArrayList<MoimAddress>()
    val title = MutableLiveData<String>()
    private val binding by viewBinding(ActivityLocationBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }

        val fromMoim = intent.getBooleanExtra(IntentParam.LOCATION_TO_ADDRESS, false)

        if (fromMoim) {
            binding.txtTitle.text = getString(R.string.title_address)
        } else {
            binding.txtTitle.text = getString(R.string.title_location_choice)
        }

        locationAdapter = LocationAdapter {
            val intent = Intent()
            intent.putExtra(IntentParam.LOCATION, it)
            setResult(RESULT_OK, intent)
            finish()
        }

        addressAdapter = AddressAdapter {
            val intent = Intent().putExtra(IntentParam.ADDRESS, Gson().toJson(it))
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        if (fromMoim) {
            binding.etLocation.hint = getString(R.string.alm_input_location)
            binding.icSearch.visible()
            binding.recycler.adapter = addressAdapter

            binding.icSearch.setOnClickListener {
                val query = binding.etLocation.text.toString()
                binding.recycler.setEmptyText(
                    String.format(getString(R.string.alm_empty_data_for_query), query)
                )

                if (query.isNotEmpty()) {
                    address.clear()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        geocoder.getFromLocationName(query, 10) { list ->
                            list.forEach {
                                val target = replace(it.getAddressLine(0))
                                address.add(
                                    MoimAddress("$target [$query]", it.latitude, it.longitude)
                                )
                            }
                        }
                    } else {
                        @Suppress("DEPRECATION") // deprecated over SDK VERSION 33
                        val result = geocoder.getFromLocationName(query, 10)
                        result?.forEach {
                            val target = replace(it.getAddressLine(0))
                            address.add(
                                MoimAddress("$target [$query]", it.latitude, it.longitude)
                            )
                        }
                    }
                    addressAdapter.updateList(address)
                }
            }
        } else {
            binding.recycler.adapter = locationAdapter

            val locations =
                resources.getStringArray(R.array.location).toCollection(ArrayList())

            locationAdapter.updateList(locations)

            binding.etLocation.afterTextChanged { edit ->
                binding.recycler.setEmptyText(
                    String.format(getString(R.string.alm_empty_data_for_query), edit)
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
    }
}