package com.moondroid.project01_meetingapp.presentation.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.moondroid.damoim.common.Extension.startActivityWithAnim
import com.moondroid.damoim.common.IntentParam
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.databinding.ItemGroupMemberBinding
import com.moondroid.project01_meetingapp.domain.model.DMUser
import com.moondroid.project01_meetingapp.presentation.ui.activity.ProfileActivity
import com.moondroid.project01_meetingapp.utils.gone
import com.moondroid.project01_meetingapp.utils.startActivityWithAnim
import com.moondroid.project01_meetingapp.utils.visible
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
        if (position != RecyclerView.NO_POSITION) {
            val profile: Profile = userList[position]
            holder.bind(profile)

            holder.itemView.setOnClickListener {
                onClick(profile)

            }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(
        private val binding: ItemGroupMemberBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val tvMaster: TextView = binding.tvMaster

        fun bind(user: DMUser) {
            binding.userDetail = user
            if (DMApp.group.masterId == user.id) {
                tvMaster.visible()
            } else {
                tvMaster.gone(true)
            }
        }
    }
}