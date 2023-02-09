package com.moondroid.project01_meetingapp.presentation.view.home.map

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseFragment
import com.moondroid.project01_meetingapp.databinding.FragmentHomeLocationBinding
import com.moondroid.project01_meetingapp.domain.model.Moim
import com.moondroid.project01_meetingapp.presentation.view.home.HomeActivity
import com.moondroid.project01_meetingapp.presentation.view.home.HomeViewModel
import com.moondroid.project01_meetingapp.presentation.view.home.map.MapViewModel.*
import com.moondroid.project01_meetingapp.utils.*
import com.moondroid.project01_meetingapp.utils.DMUtils
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentHomeLocationBinding>(R.layout.fragment_home_location),
    OnMapReadyCallback {

    lateinit var activity: HomeActivity
    private lateinit var mNaverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private val viewModel: MapViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
    }

    override fun init() {
        repeatOnStarted {
            viewModel.eventFlow.collect {
                handleEvent(it)
            }
        }
    }

    private fun initView(savedInstanceState: Bundle?) {
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, 0xff)
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.Update ->  {
                event.list.forEach { item ->
                    if (DMUtils.beforeDate(item.date, "yyyy.MM.dd")) {
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

    //MapView 사용시 프레그먼트, 액티비티의 생명주기에 따른 MapView 생명주기를 호출해 줘야 함.
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}