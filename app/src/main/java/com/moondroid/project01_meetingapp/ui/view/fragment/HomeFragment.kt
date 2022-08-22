package com.moondroid.project01_meetingapp.ui.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseFragment
import com.moondroid.project01_meetingapp.databinding.FragmentHomeGroupListBinding
import com.moondroid.project01_meetingapp.databinding.FragmentHomeLocationBinding
import com.moondroid.project01_meetingapp.databinding.FragmentHomeMyGroupBinding
import com.moondroid.project01_meetingapp.databinding.FragmentHomeSearchBinding
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.model.Moim
import com.moondroid.project01_meetingapp.ui.view.activity.HomeActivity
import com.moondroid.project01_meetingapp.ui.view.adapter.CategoryListAdapter
import com.moondroid.project01_meetingapp.ui.view.adapter.GroupListAdapter
import com.moondroid.project01_meetingapp.ui.viewmodel.HomeViewModel
import com.moondroid.project01_meetingapp.utils.Constants
import com.moondroid.project01_meetingapp.utils.DMLog
import com.moondroid.project01_meetingapp.utils.view.afterTextChanged
import com.moondroid.project01_meetingapp.utils.view.logException
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class GroupListFragment :
    BaseFragment(),
    GroupListAdapter.OnItemClickListener,
    CategoryListAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeGroupListBinding
    lateinit var activity: HomeActivity
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var groupAdapter: GroupListAdapter
    private lateinit var categoryAdapter: CategoryListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_group_list, container, false)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        viewModel.showLoading.observe(viewLifecycleOwner) {
            activity.showLoading(it)
        }

        viewModel.groupsContent.observe(viewLifecycleOwner) {
            when (it.code) {
                Constants.ResponseCode.SUCCESS -> {
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
        activity.goToGroupActivity(Constants.ActivityTy.HOME)
    }

    override fun onClick(category: String) {
        groupAdapter.updateList(category)
    }

    fun goToCreateGroupActivity(@Suppress("UNUSED_PARAMETER") vw: View) {
        activity.goToCreateGroupActivity()
    }
}

class MyGroupFragment :
    BaseFragment(),
    GroupListAdapter.OnItemClickListener {

    lateinit var binding: FragmentHomeMyGroupBinding
    lateinit var activity: HomeActivity
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var groupAdapter: GroupListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_my_group, container, false)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMyGroup(DMApp.user.id)
    }

    private fun initView() {
        groupAdapter = GroupListAdapter(activity, this)
        binding.recycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recycler.adapter = groupAdapter
    }

    private fun initViewModel() {
        viewModel.showLoading.observe(viewLifecycleOwner) {
            activity.showLoading(it)
        }

        viewModel.myGroupsContent.observe(viewLifecycleOwner) {
            DMLog.e("[HomeFragment] , MyGroupFragment , getMyGroup() Response => $it")
            when (it.code) {
                Constants.ResponseCode.SUCCESS -> {
                    val gson = GsonBuilder().create()
                    val newList = gson.fromJson<ArrayList<GroupInfo>>(
                        it.body,
                        object : TypeToken<ArrayList<GroupInfo>>() {}.type
                    )

                    groupAdapter.update(newList)
                }

                else -> {
                    activity.showError(
                        String.format(
                            getString(R.string.error_load_group_list_fail),
                            "[E01 : ${it.code}]"
                        )
                    )
                }
            }
        }
    }

    override fun onClick() {
        activity.goToGroupActivity(Constants.ActivityTy.HOME)
    }
}

class SearchFragment : BaseFragment(), GroupListAdapter.OnItemClickListener {

    lateinit var activity: HomeActivity
    private lateinit var binding: FragmentHomeSearchBinding
    private val groups = ArrayList<GroupInfo>()
    private lateinit var groupAdapter: GroupListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_search, container, false)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        activity.goToGroupActivity(Constants.ActivityTy.HOME)
    }
}

class LocationFragment : BaseFragment(), OnMapReadyCallback {

    lateinit var activity: HomeActivity
    lateinit var binding: FragmentHomeLocationBinding
    lateinit var mNaverMap: NaverMap
    lateinit var locationSource: FusedLocationSource
    private val viewModel: HomeViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_location, container, false)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
        initViewModel()
    }

    private fun initView(savedInstanceState: Bundle?) {
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, 0xff)
    }

    @SuppressLint("SimpleDateFormat")
    private fun initViewModel() {
        viewModel.showLoading.observe(viewLifecycleOwner) {
            activity.showLoading(it)
        }

        viewModel.moimResponse.observe(viewLifecycleOwner) {

            DMLog.e("[LocationFragment] , moimResponse , Response => $it")

            when (it.code) {
                Constants.ResponseCode.SUCCESS -> {
                    val gson = GsonBuilder().create()
                    val newList = gson.fromJson<ArrayList<Moim>>(
                        it.body,
                        object : TypeToken<ArrayList<Moim>>() {}.type
                    )

                    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd")

                    newList.forEach { item ->
                        val date = simpleDateFormat.parse(item.date)
                        if (date != null) {
                            if (date.after(Date(System.currentTimeMillis()))) {
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
                                            return String.format(
                                                "%s\n%s\n%s",
                                                item.title,
                                                item.date,
                                                item.time
                                            )
                                        }
                                    }

                                infoWindow.open(marker)
                            }
                        } else {
                            logException(Exception("[LocationFragment] , Moim Date Parse Exception"))
                        }
                    }
                }

                else -> {
                    activity.showError(
                        String.format(
                            activity.getString(R.string.error_load_moim_list_fail),
                            "[E01 : ${it.code}]"
                        )
                    )
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
    //fragment 사용하는 걸 권장하지만, fragment 사용시 마커가 잘 생성되지 않는 버그가 있음.
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