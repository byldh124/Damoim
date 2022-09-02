package com.moondroid.project01_meetingapp.ui.view.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityMoimBinding
import com.moondroid.project01_meetingapp.model.Address
import com.moondroid.project01_meetingapp.model.Moim
import com.moondroid.project01_meetingapp.ui.viewmodel.MoimViewModel
import com.moondroid.project01_meetingapp.utils.ActivityTy
import com.moondroid.project01_meetingapp.utils.IntentParam
import com.moondroid.project01_meetingapp.utils.RequestParam
import com.moondroid.project01_meetingapp.utils.ResponseCode
import com.moondroid.project01_meetingapp.utils.view.log
import com.moondroid.project01_meetingapp.utils.view.logException
import com.moondroid.project01_meetingapp.utils.view.toast
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.DateTime

@AndroidEntryPoint
class MoimActivity : BaseActivity<ActivityMoimBinding>(R.layout.activity_moim), OnMapReadyCallback {
    private val requestCode = -1
    private var temp: Address? = null
    private val viewModel: MoimViewModel by viewModels()
    private lateinit var locationSource: FusedLocationSource
    private lateinit var mNaverMap: NaverMap

    override fun init() {
        binding.activity = this
        initView()
        initViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, requestCode)
    }

    /* 관심지역 ActivityResult */
    private val getAddress =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result?.resultCode == RESULT_OK) {
                    result.data?.let {
                        val json = it.getStringExtra(IntentParam.ADDRESS).toString()
                        temp = Gson().fromJson(json, Address::class.java)
                        temp?.let { address ->
                            binding.tvLocation.text = address.address
                            val marker = Marker(address.latLng)
                            marker.map = mNaverMap
                            mNaverMap.cameraPosition =
                                CameraPosition(address.latLng, 16.0, 0.0, 0.0)
                        }
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }
    }

    private fun initViewModel() {
        viewModel.showLoading.observe(this) {
            showLoading(it)
        }

        viewModel.showError.observe(this) {
            showNetworkError(it)
        }

        viewModel.moimResponse.observe(this) {
            when (it.code) {
                ResponseCode.SUCCESS -> {
                    showMessage("새로운 모임을 만들었습니다.") {
                        finish()
                    }
                }
                else -> {
                    showMessage(String.format("모임 만들기에 실패했습니다. [%s]", "E01: ${it.code}"))
                }
            }
        }
    }

    fun toLocation(@Suppress("UNUSED_PARAMETER") vw: View) {
        getAddress.launch(
            Intent(this, LocationActivity::class.java)
                .putExtra(IntentParam.ACTIVITY, ActivityTy.MOIM)
        )
    }


    fun showDate(@Suppress("UNUSED_PARAMETER") vw: View) {
        val date = DateTime(System.currentTimeMillis())
        val datePicker = DatePickerDialog(
            this,
            { _, p1, p2, p3 ->
                binding.tvDate.text = String.format("%d.%d.%d", p1, p2 + 1, p3)
            }, date.year, date.monthOfYear - 1, date.dayOfMonth
        )

        datePicker.show()
    }

    fun showTime(@Suppress("UNUSED_PARAMETER") vw: View) {
        val timePicker = TimePickerDialog(
            this,
            { _, hour, minute ->
                binding.tvTime.text = String.format("%02d : %02d", hour, minute)
            }, 12, 0, true
        )

        timePicker.show()
    }

    fun save(@Suppress("UNUSED_PARAMETER") vw: View) {
        if (temp == null) return
        val address = temp!!.address
        val lat = temp!!.latLng.latitude
        val lng = temp!!.latLng.longitude
        val date = binding.tvDate.text.toString()
        val time = binding.tvTime.text.toString()
        var pay = binding.tvPay.text.toString()

        if (date.isEmpty()) {
            toast("모임 날짜를 입력해주세요.")
            return
        } else if (time.isEmpty()) {
            toast("모임 시간을 입력해주세요.")
            return
        } else {
            if (pay.isEmpty()) {
                pay = "0원"
            }
            val joinMember = Gson().toJson(ArrayList<String>().add(DMApp.user.id))
            val body = JsonObject()
            body.addProperty(RequestParam.TITLE, DMApp.group.title)
            body.addProperty(RequestParam.ADDRESS, address)
            body.addProperty(RequestParam.DATE, date)
            body.addProperty(RequestParam.TIME, time)
            body.addProperty(RequestParam.PAY, pay)
            body.addProperty(RequestParam.PAY, pay)
            body.addProperty(RequestParam.LAT, lat)
            body.addProperty(RequestParam.LNG, lng)
            body.addProperty(RequestParam.JOIN_MEMBER, joinMember)

            log("createMoim() , body => $body")

            viewModel.createMoim(body)
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