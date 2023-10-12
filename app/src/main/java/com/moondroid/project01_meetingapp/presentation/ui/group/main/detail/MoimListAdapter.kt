package com.moondroid.project01_meetingapp.presentation.ui.group.main.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.damoim.domain.model.MoimItem
import com.moondroid.project01_meetingapp.databinding.ItemMoimBinding
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class MoimListAdapter(private val onClick: (MoimItem) -> Unit) :
    RecyclerView.Adapter<MoimListAdapter.ViewHolder>() {

    private var moimList: List<MoimItem> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun updateList(newMoimList: List<MoimItem>) {
        moimList = newMoimList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMoimBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val moim: MoimItem = moimList[position]
            holder.bind(moim)
            holder.itemView.setOnClickListener {
                onClick(moim)
            }
        }
    }

    override fun getItemCount(): Int {
        return moimList.size
    }

    inner class ViewHolder(
        private val binding: ItemMoimBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(moim: MoimItem) {
            binding.moim = moim
            binding.executePendingBindings()
        }

    }
}