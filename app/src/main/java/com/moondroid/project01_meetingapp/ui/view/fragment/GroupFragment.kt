package com.moondroid.project01_meetingapp.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseFragment
import com.moondroid.project01_meetingapp.databinding.FragmentGroupInfoBinding
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.ui.view.activity.GroupActivity
import com.moondroid.project01_meetingapp.ui.view.adapter.MemberListAdapter
import com.moondroid.project01_meetingapp.ui.viewmodel.GroupViewModel
import com.moondroid.project01_meetingapp.utils.Constants
import com.moondroid.project01_meetingapp.utils.DMLog
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.log

class InfoFragment : BaseFragment() {

    lateinit var binding: FragmentGroupInfoBinding
    private var activity: GroupActivity? = null
    private val viewModel: GroupViewModel by viewModel()
    private lateinit var adapter: MemberListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as GroupActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_group_info, container, false)
        binding.activity = activity
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() {
        adapter = activity?.let {
            MemberListAdapter(it)
        }!!

        binding.recMember.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recMember.adapter = adapter
    }

    private fun initViewModel() {
        activity?.groupInfo?.let {
            viewModel.loadMember(it.title)
        }

        viewModel.memberResponse.observe(viewLifecycleOwner) {
            DMLog.e("[GroupFragment] , GroupInfoFragment , getMember() Response => $it")
            when (it.code) {
                Constants.ResponseCode.SUCCESS -> {
                    val body = it.body.asJsonArray
                    val gson = Gson()
                    val member = gson.fromJson<ArrayList<User>>(body, object : TypeToken<ArrayList<User>>(){}.type)
                    adapter.updateList(member)
                }
                else -> {

                }
            }
        }
    }
}

class BoardFragment : BaseFragment() {

    private var activity: GroupActivity? = null
    private val viewModel: GroupViewModel by viewModel()
    private lateinit var adapter: MemberListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as GroupActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_group_board, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() {

    }

    private fun initViewModel() {

    }
}

class GalleryFragment : BaseFragment() {

    private var activity: GroupActivity? = null
    private val viewModel: GroupViewModel by viewModel()
    private lateinit var adapter: MemberListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as GroupActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_group_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() {

    }

    private fun initViewModel() {

    }
}

class ChatFragment : BaseFragment() {

    private var activity: GroupActivity? = null
    private val viewModel: GroupViewModel by viewModel()
    private lateinit var adapter: MemberListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as GroupActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_group_chat, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() {

    }

    private fun initViewModel() {

    }
}