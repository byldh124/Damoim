package com.moondroid.project01_meetingapp.presentation.view.location

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.project01_meetingapp.databinding.ItemLocationBinding
import com.moondroid.project01_meetingapp.utils.IntentParam
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class LocationAdapter(private val onClick : (String) -> Unit) :
    RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    private var list: List<String> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun updateList(newList: List<String>) {
        list = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val address = list[position]
            bind(address)
            itemView.setOnClickListener {
                onClick(address)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(
        private val binding: ItemLocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(address: String) {
            binding.address = address
            binding.executePendingBindings()
        }

    }
}