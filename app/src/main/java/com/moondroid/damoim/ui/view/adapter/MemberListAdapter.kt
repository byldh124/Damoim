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
import com.moondroid.damoim.model.User
import com.moondroid.damoim.utils.DMUtils
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_member.view.*
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class MemberListAdapter(val ctx: Context) : RecyclerView.Adapter<MemberListAdapter.ViewHolder>() {

    private var userList: List<User> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun updateList(newUserList: List<User>) {
        userList = newUserList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(ctx).inflate(R.layout.item_member, parent, false)
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
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val ivThumb: CircleImageView = itemView.ivMemberThumb
        private val tvName: TextView = itemView.tvName
        private val tvMsg: TextView = itemView.tvMsg
        private val tvMaster: TextView = itemView.tvMaster

        fun bind(user: User) {
            Glide
                .with(ctx)
                .load(DMUtils.getImgUrl(user.thumb))
                .into(ivThumb)

            tvName.text = user.name
            tvMsg.text = user.msg
        }

    }
}