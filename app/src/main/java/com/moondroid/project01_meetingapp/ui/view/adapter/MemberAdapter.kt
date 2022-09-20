package com.moondroid.project01_meetingapp.ui.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.databinding.ItemGroupMemberBinding
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.ui.view.activity.ProfileActivity
import com.moondroid.project01_meetingapp.utils.IntentParam
import com.moondroid.project01_meetingapp.utils.view.gone
import com.moondroid.project01_meetingapp.utils.view.startActivityWithAnim
import com.moondroid.project01_meetingapp.utils.view.visible
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class MemberAdapter(private val activity: AppCompatActivity) :
    RecyclerView.Adapter<MemberAdapter.ViewHolder>() {

    private var userList: List<User> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun updateList(newUserList: List<User>) {
        userList = newUserList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGroupMemberBinding.inflate(LayoutInflater.from(activity), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val user: User = userList[position]
            holder.bind(user)

            holder.itemView.setOnClickListener {
                val intent = Intent(activity, ProfileActivity::class.java)
                    .apply {
                        putExtra(IntentParam.USER, Gson().toJson(user))
                        addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    }
                activity.startActivityWithAnim(intent)
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

        fun bind(user: User) {
            binding.userDetail = user
            if (DMApp.group.masterId == user.id) {
                tvMaster.visible()
            } else {
                tvMaster.gone(true)
            }
        }
    }
}