package com.moondroid.project01_meetingapp.presentation.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.project01_meetingapp.databinding.ItemGalleryDialogBinding
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class GalleryDialogAdapter(
    private val context: Context
) : RecyclerView.Adapter<GalleryDialogAdapter.ViewHolder>() {

    private var list: List<String> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun update(newList: List<String>){
        list = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGalleryDialogBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(
        private val binding: ItemGalleryDialogBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) {
            binding.url = url
            binding.executePendingBindings()
        }
    }
}