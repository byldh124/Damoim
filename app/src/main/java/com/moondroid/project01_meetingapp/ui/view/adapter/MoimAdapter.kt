package com.moondroid.project01_meetingapp.ui.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.moondroid.project01_meetingapp.databinding.ItemGroupMemberBinding
import com.moondroid.project01_meetingapp.databinding.ItemMoimBinding
import com.moondroid.project01_meetingapp.model.Moim
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.ui.view.activity.GroupActivity
import com.moondroid.project01_meetingapp.ui.view.activity.MoimInfoActivity
import com.moondroid.project01_meetingapp.utils.ActivityTy
import com.moondroid.project01_meetingapp.utils.IntentParam
import com.moondroid.project01_meetingapp.utils.view.gone
import com.moondroid.project01_meetingapp.utils.view.startActivityWithAnim
import com.moondroid.project01_meetingapp.utils.view.visible
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class MoimAdapter(private val activity: GroupActivity) :
    RecyclerView.Adapter<MoimAdapter.ViewHolder>() {

    private var moimList: List<Moim> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun updateList(newMoimList: List<Moim>) {
        moimList = newMoimList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMoimBinding.inflate(LayoutInflater.from(activity))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val moim: Moim = moimList[position]
            holder.bind(moim)

            holder.itemView.setOnClickListener {
                activity.startActivityWithAnim(
                    Intent(activity, MoimInfoActivity::class.java)
                        .putExtra(IntentParam.MOIM, Gson().toJson(moim))
                        .putExtra(IntentParam.ACTIVITY, ActivityTy.GROUP)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return moimList.size
    }

    inner class ViewHolder(
        private val binding: ItemMoimBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(moim: Moim) {
            binding.moim = moim
            binding.executePendingBindings()
        }

    }
}