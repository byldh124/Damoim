package com.moondroid.damoim.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.moondroid.damoim.R
import com.moondroid.damoim.application.DMApp
import com.moondroid.damoim.base.BaseFragment
import com.moondroid.damoim.databinding.FragmentGroupInfoBinding
import com.moondroid.damoim.ui.view.activity.GroupActivity
import com.moondroid.damoim.ui.view.adapter.MemberListAdapter
import com.moondroid.damoim.ui.viewmodel.GroupViewModel
import org.koin.android.viewmodel.ext.android.viewModel

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
            MemberListAdapter(
                it
            )
        }!!
        binding.recycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recycler.adapter = adapter
    }

    private fun initViewModel() {
        activity?.groupInfo?.let { viewModel.loadMember(it.meetName) }

        viewModel.memberContent.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                adapter.updateList(it)
            }
        })
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
        val view = inflater.inflate(R.layout.fragment_group_gallery, container, false)
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