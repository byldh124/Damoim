package com.moondroid.project01_meetingapp.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseFragment
import com.moondroid.project01_meetingapp.databinding.FragmentHomeGroupListBinding
import com.moondroid.project01_meetingapp.databinding.FragmentHomeLocationBinding
import com.moondroid.project01_meetingapp.databinding.FragmentHomeMyGroupBinding
import com.moondroid.project01_meetingapp.databinding.FragmentHomeSearchBinding
import com.moondroid.project01_meetingapp.domain.model.GroupInfo
import com.moondroid.project01_meetingapp.domain.model.Moim
import com.moondroid.project01_meetingapp.presentation.view.activity.HomeActivity
import com.moondroid.project01_meetingapp.presentation.view.adapter.CategoryListAdapter
import com.moondroid.project01_meetingapp.presentation.view.adapter.GroupListAdapter
import com.moondroid.project01_meetingapp.presentation.viewmodel.HomeViewModel
import com.moondroid.project01_meetingapp.utils.ActivityTy
import com.moondroid.project01_meetingapp.utils.DMUtils
import com.moondroid.project01_meetingapp.utils.ResponseCode
import com.moondroid.project01_meetingapp.utils.afterTextChanged
import com.moondroid.project01_meetingapp.utils.log
import com.moondroid.project01_meetingapp.utils.logException
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class GroupListFragment :
    BaseFragment<FragmentHomeGroupListBinding>(R.layout.fragment_home_group_list),
    GroupListAdapter.OnItemClickListener,
    CategoryListAdapter.OnItemClickListener {

    lateinit var activity: HomeActivity
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var groupAdapter: GroupListAdapter
    private lateinit var categoryAdapter: CategoryListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun init() {
        binding.fragment = this
        initView()
        initViewModel()
    }

    private fun initView() {
        groupAdapter = GroupListAdapter(activity, this)

        binding.recGroup.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        binding.recGroup.adapter = groupAdapter

        categoryAdapter = CategoryListAdapter(
            activity,
            this
        )

        binding.recCategory.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        binding.recCategory.adapter = categoryAdapter

    }

    override fun onResume() {
        super.onResume()
        viewModel.loadGroup()
    }

    /**
     * ViewModel 초기화
     */
    private fun initViewModel() {
        viewModel.groupsContent.observe(viewLifecycleOwner) {
            when (it.code) {
                ResponseCode.SUCCESS -> {
                    log("loadGroup() , Response => $it")

                    val result = it.body.asJsonArray
                    val gson = GsonBuilder().create()
                    val groups = gson.fromJson<ArrayList<GroupInfo>>(
                        result,
                        object : TypeToken<ArrayList<GroupInfo>>() {}.type
                    )
                    activity.groupsList = groups

                    groupAdapter.updateList(groups)
                }
            }
        }
    }

    override fun onClick() {
        activity.goToGroupActivity(ActivityTy.HOME)
    }

    override fun onClick(category: String) {
        groupAdapter.updateList(category)
        binding.recGroup.setEmptyText(String.format(getString(R.string.alm_empty_data_for_query), category))
    }

    fun goToCreateGroupActivity(@Suppress("UNUSED_PARAMETER") vw: View) {
        activity.goToCreateGroupActivity()
    }
}

@AndroidEntryPoint
class MyGroupFragment :
    BaseFragment<FragmentHomeMyGroupBinding>(R.layout.fragment_home_my_group),
    GroupListAdapter.OnItemClickListener {

    lateinit var activity: HomeActivity
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var groupAdapter: GroupListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun init() {
        binding.fragment = this
        initView()
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMyGroup(activity.user!!.id)
    }

    private fun initView() {
        groupAdapter = GroupListAdapter(activity, this)
        binding.recycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recycler.adapter = groupAdapter
        binding.recycler.setEmptyText(getString(R.string.alm_my_group_empty))
    }

    private fun initViewModel() {

        viewModel.myGroupsContent.observe(viewLifecycleOwner) {
            log("MyGroupFragment , getMyGroup() Response => $it")
            when (it.code) {
                ResponseCode.SUCCESS -> {
                    val gson = GsonBuilder().create()
                    val newList = gson.fromJson<ArrayList<GroupInfo>>(
                        it.body,
                        object : TypeToken<ArrayList<GroupInfo>>() {}.type
                    )

                    groupAdapter.update(newList)
                }

                else -> {
                    activity.showMessage(getString(R.string.error_load_group_list_fail), "[E01 : ${it.code}]")
                }
            }
        }
    }

    override fun onClick() {
        activity.goToGroupActivity(ActivityTy.HOME)
    }
}

class SearchFragment : BaseFragment<FragmentHomeSearchBinding>(R.layout.fragment_home_search),
    GroupListAdapter.OnItemClickListener {

    lateinit var activity: HomeActivity
    private val groups = ArrayList<GroupInfo>()
    private lateinit var groupAdapter: GroupListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun init() {
        binding.fragment = this
        initView()
    }

    private fun initView() {
        try {
            groupAdapter = GroupListAdapter(activity, this)
            binding.recycler.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            binding.recycler.adapter = groupAdapter

            binding.etQuery.afterTextChanged { query ->
                groups.clear()
                binding.recycler.setEmptyText(String.format(getString(R.string.alm_search_data_empty), query))
                activity.groupsList.forEach {
                    if (query.isEmpty()) return@forEach
                    if (
                        it.title.contains(query) ||
                        it.purpose.contains(query) ||
                        it.information.contains(query) ||
                        it.interest.contains(query)
                    ) {
                        groups.add(it)
                    }
                }

                groupAdapter.update(groups)
            }

        } catch (e: Exception) {
            logException(e)
        }
    }

    override fun onClick() {
        activity.goToGroupActivity(ActivityTy.HOME)
    }
}

@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentHomeLocationBinding>(R.layout.fragment_home_location),
    OnMapReadyCallback {

    lateinit var activity: HomeActivity
    private lateinit var mNaverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private val viewModel: HomeViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
    }

    override fun init() {
        binding.fragment = this
        initViewModel()
    }

    private fun initView(savedInstanceState: Bundle?) {
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, 0xff)
    }

    private fun initViewModel() {
        viewModel.moimResponse.observe(viewLifecycleOwner) {

            log("moimResponse , Response => $it")

            when (it.code) {
                ResponseCode.SUCCESS -> {
                    val gson = GsonBuilder().create()
                    val newList = gson.fromJson<ArrayList<Moim>>(
                        it.body,
                        object : TypeToken<ArrayList<Moim>>() {}.type
                    )

                    newList.forEach { item ->
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

                else -> {
                    activity.showMessage(getString(R.string.error_load_moim_list_fail), "[E01 : ${it.code}]")
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