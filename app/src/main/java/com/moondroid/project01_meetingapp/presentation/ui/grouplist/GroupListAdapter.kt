package com.moondroid.project01_meetingapp.presentation.ui.grouplist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.project01_meetingapp.databinding.ItemHomeGroupInfoBinding

@SuppressLint("NotifyDataSetChanged")
class GroupListAdapter(
    private val onClick: (GroupItem) -> Unit,
) : ListAdapter<GroupItem, GroupListAdapter.ViewHolder>(GroupItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeGroupInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group: GroupItem = getItem(position)
        holder.container.setOnClickListener {
            onClick(group)
        }

        holder.bind(group)
    }

    inner class ViewHolder(
        private val binding: ItemHomeGroupInfoBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        val container: RelativeLayout = binding.container

        fun bind(groupInfo: GroupItem) {
            binding.groupDetail = groupInfo
            binding.executePendingBindings()
        }

    }

    object GroupItemDiffCallback : DiffUtil.ItemCallback<GroupItem>() {
        override fun areItemsTheSame(oldItem: GroupItem, newItem: GroupItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GroupItem, newItem: GroupItem): Boolean {
            return oldItem.title == newItem.title
        }
    }
}