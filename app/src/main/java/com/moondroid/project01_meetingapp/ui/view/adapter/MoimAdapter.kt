package com.moondroid.project01_meetingapp.ui.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.project01_meetingapp.databinding.ItemGroupMemberBinding
import com.moondroid.project01_meetingapp.databinding.ItemMoimBinding
import com.moondroid.project01_meetingapp.model.Moim
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.utils.view.gone
import com.moondroid.project01_meetingapp.utils.view.visible
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class MoimAdapter(private val ctx: Context) :
    RecyclerView.Adapter<MoimAdapter.ViewHolder>() {

    private var moimList: List<Moim> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun updateList(newMoimList: List<Moim>) {
        moimList = newMoimList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMoimBinding.inflate(LayoutInflater.from(ctx))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val moim:Moim = moimList[position]
            holder.bind(moim)
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