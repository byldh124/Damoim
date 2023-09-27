package com.moondroid.project01_meetingapp.presentation.ui.moim

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import com.google.gson.Gson
import com.moondroid.damoim.common.ActivityTy
import com.moondroid.project01_meetingapp.utils.ViewExtension.init
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.project01_meetingapp.utils.ViewExtension.repeatOnStarted
import com.moondroid.damoim.common.IntentParam
import com.moondroid.damoim.domain.model.MoimAddress
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivityMoimBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.location.LocationActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class MoimActivity : BaseActivity(), OnMapReadyCallback {
    private val viewModel: MoimViewModel by viewModels()
    private val binding by viewBinding(ActivityMoimBinding::inflate)

    private val requestCode = 0xf0f0
    private lateinit var locationSource: FusedLocationSource
    private lateinit var mNaverMap: NaverMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repeatOnStarted {
            viewModel.eventFlow.collect {
                handleEvent(it)
            }
        }
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, requestCode)

        binding.lifecycleOwner = this
        binding.model = viewModel
        binding.toolbar.init(this)
    }


    fun toLocation() {
        registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { intent ->
                    val json = intent.getStringExtra(IntentParam.ADDRESS).toString()
                    val temp = Gson().fromJson(json, MoimAddress::class.java)
                    temp?.let { address ->
                        binding.tvLocation.text = address.address
                        val marker = Marker(LatLng(address.lat, address.lng))
                        marker.map = mNaverMap
                        mNaverMap.cameraPosition = CameraPosition(LatLng(address.lat, address.lng), 16.0, 0.0, 0.0)
                        viewModel.address = address
                    }
                }
            }
        }.launch(
            Intent(this, LocationActivity::class.java).apply {
                putExtra(IntentParam.ACTIVITY, ActivityTy.MOIM)
            }
        )
    }


    private fun showDate() {
        try {
            val date = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this, R.style.DatePickerSpin, { _, p1, p2, p3 ->
                    viewModel.date.value = String.format("%d.%d.%d", p1, p2 + 1, p3)
                }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun showTime() {
        try {
            val timePicker = TimePickerDialog(
                this, R.style.TimePickerSpin, { _, hour, minute ->
                    viewModel.time.value = String.format("%02d : %02d", hour, minute)
                }, 12, 0, true
            )
            timePicker.show()
        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun handleEvent(event: MoimViewModel.Event) {
        when (event) {
            MoimViewModel.Event.Date -> showDate()
            MoimViewModel.Event.Location -> toLocation()
            MoimViewModel.Event.Time -> showTime()
        }
    }

    override fun onMapReady(map: NaverMap) {
        try {
            mNaverMap = map

            //보여지는 화면 설정
            map.mapType = NaverMap.MapType.Basic

            map.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true)
            map.isIndoorEnabled = true

            val cameraPosition = CameraPosition(LatLng(37.5670135, 126.9783740), 16.0, 0.0, 0.0)
            map.cameraPosition = cameraPosition

            mNaverMap.locationSource = locationSource
            val uiSettings = mNaverMap.uiSettings
            mNaverMap.locationTrackingMode = LocationTrackingMode.Follow

            uiSettings.apply {
                isCompassEnabled = true
                isLocationButtonEnabled = true
                isLogoClickEnabled = true
                isScrollGesturesEnabled = true
                isZoomControlEnabled = true
                isRotateGesturesEnabled = true
            }

        } catch (e: Exception) {
            logException(e)
        }
    }

    //맵뷰 사용시 액티비티의 생명주기에 따른 맵뷰 생명주기 설정.
    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

}