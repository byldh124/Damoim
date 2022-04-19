package com.moondroid.damoim.ui.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moondroid.damoim.R
import com.moondroid.damoim.databinding.ItemGroupMemberBinding
import com.moondroid.damoim.model.User
import com.moondroid.damoim.utils.DMUtils
import com.moondroid.damoim.utils.view.gone
import com.moondroid.damoim.utils.view.visible
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class MemberListAdapter(private val ctx: Context) :
    RecyclerView.Adapter<MemberListAdapter.ViewHolder>() {

    private var userList: List<User> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun updateList(newUserList: List<User>) {
        userList = newUserList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGroupMemberBinding.inflate(LayoutInflater.from(ctx))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val user: User = userList[position]
            holder.bind(user)
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
            if (adapterPosition == 0) {
                tvMaster.visible()
            } else {
                tvMaster.gone(true)
            }
        }

    }
}