package com.moondroid.project01_meetingapp.presentation.ui.home.map

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.FragmentHomeLocationBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseFragment
import com.moondroid.project01_meetingapp.presentation.base.viewModel
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.home.HomeActivity
import com.moondroid.project01_meetingapp.presentation.ui.home.map.MapViewModel.Event
import com.moondroid.project01_meetingapp.utils.ViewExtension.collectEvent
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class LocationFragment : BaseFragment(R.layout.fragment_home_location),
    OnMapReadyCallback {

    private val binding by viewBinding(FragmentHomeLocationBinding::bind)
    private val viewModel: MapViewModel by viewModel()

    private lateinit var mapView: MapView

    lateinit var activity: HomeActivity
    private lateinit var mNaverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectEvent(event = viewModel.eventFlow, collector = ::handleEvent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, 0xff)
    }

    private fun handleEvent(event: Event) {
        when (event) {
            // 모임정보 불러오 이후 마커 표시
            is Event.Update -> {
                event.list.forEach { item ->
                    if (beforeDate(item.date, "yyyy.MM.dd")) {
                        val marker = Marker()
                        marker.apply {
                            position = LatLng(item.lat, item.lng)
                            map = mNaverMap
                            width = Marker.SIZE_AUTO
                            height = Marker.SIZE_AUTO
                        }
                        val infoWindow = InfoWindow()

                        infoWindow.adapter =
                            object : InfoWindow.DefaultTextAdapter(activity) {
                                override fun getText(p0: InfoWindow): CharSequence {
                                    return String.format("%s\n%s\n%s", item.title, item.date, item.time)
                                }
                            }

                        infoWindow.open(marker)
                    }
                }
            }
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

            viewModel.getMoim()
        } catch (e: Exception) {
            logException(e)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun beforeDate(date: String, pattern: String): Boolean {
        var result = false
        try {
            val format = SimpleDateFormat(pattern)
            val targetDate = format.parse(date)
            val toDay = Date(System.currentTimeMillis())
            result = targetDate!!.after(toDay)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    //MapView 사용시 프레그먼트, 액티비티의 생명주기에 따른 MapView 생명주기를 호출해 줘야 함.
    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}