package com.moondroid.project01_meetingapp.ui.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.databinding.ItemHomeGroupInfoBinding
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.utils.DMLog
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class GroupListAdapter(
    private val ctx: Context,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<GroupListAdapter.ViewHolder>() {

    private lateinit var listContainer: List<GroupInfo>

    private var groupList: List<GroupInfo> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    private var currentCategory: String = CATEGORY_ALL

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHomeGroupInfoBinding.inflate(LayoutInflater.from(ctx)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group: GroupInfo = groupList[position]
        holder.container.setOnClickListener {
            /* FIXME
            * 권장하는 아이템 클릭 프로세스
            * 모임의 이름만 파라미터로 넘겨준뒤 해당 이름을 인텐트 엑스트라를 통해 GroupActivity로 전달
            * GroupActivity에서 해당 모임 이름값으로 api 호출로 detail group info 를 전달 받음
            *
            * 현재 구성된 클릭 이벤트 프로세스
            * 아이템 클릭시 해당 모임의 정보를 글로벌로 올린뒤 GroupActivity에서
            * 글로벌에 저장된 해당 정보를 조회하여 표현
            **/
            DMApp.group = group
            listener.onClick()
        }

        holder.bind(group)
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    fun update(newGroupList: List<GroupInfo>) {
        groupList = newGroupList
    }

    fun updateList(newGroupList: List<GroupInfo>) {
        listContainer = newGroupList
        setList()
    }

    fun updateList(category: String) {
        currentCategory = category
        setList()
    }

    private fun setList() {
        val sampleList: MutableList<GroupInfo> = ArrayList()
        listContainer.forEach() {
            if (it.interest == currentCategory
                || currentCategory == CATEGORY_ALL
            ) {
                sampleList.add(it)
            }
        }
        groupList = sampleList
    }

    inner class ViewHolder(
        private val binding: ItemHomeGroupInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val container: RelativeLayout = binding.container

        fun bind(groupInfo: GroupInfo) {
            binding.groupDetail = groupInfo
            binding.executePendingBindings()
        }

    }

    interface OnItemClickListener {
        fun onClick()
    }

    companion object {
        const val CATEGORY_ALL: String = "전체"
    }
}