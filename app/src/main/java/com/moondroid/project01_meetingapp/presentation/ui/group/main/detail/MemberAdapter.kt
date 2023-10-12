package com.moondroid.project01_meetingapp.presentation.ui.group.main.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.project01_meetingapp.utils.ViewExtension.visible
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.databinding.ItemGroupMemberBinding
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class MemberAdapter(private val onClick: (Profile) -> Unit) :
    RecyclerView.Adapter<MemberAdapter.ViewHolder>() {

    private var userList: List<Profile> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun updateList(newUserList: List<Profile>) {
        userList = newUserList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGroupMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    inner class ViewHolder(
        private val binding: ItemGroupMemberBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val tvMaster: TextView = binding.tvMaster

        fun bind(profile: Profile) {
            binding.profile = profile
            tvMaster.visible(DMApp.group.masterId == profile.id)
            binding.root.setOnClickListener {
                onClick(profile)
            }
        }
    }
}
