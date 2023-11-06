package com.moondroid.project01_meetingapp.presentation.ui.common.interest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.project01_meetingapp.databinding.ItemInterestBinding

class InterestAdapter(
    private val onClick: (position: Int) -> Unit
) : RecyclerView.Adapter<InterestAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemInterestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 19
    }

    inner class ViewHolder(
        private val binding: ItemInterestBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.position = position + 1
            itemView.setOnClickListener {
                onClick(position)
            }
            binding.executePendingBindings()
        }
    }
}