package com.moondroid.project01_meetingapp.presentation.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.project01_meetingapp.databinding.ItemGalleryBinding
import com.moondroid.project01_meetingapp.presentation.dialog.GalleryDialog
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class GalleryAdapter(
    private val context: Context
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private var galleryDialog: GalleryDialog? = null

    private var list: List<String> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun update(newList: List<String>) {
        list = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGalleryBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            if (galleryDialog == null) {
                galleryDialog = GalleryDialog(context, list)
            }

            galleryDialog?.let { dialog ->
                dialog.position = position
                dialog.show()
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(
        private val binding: ItemGalleryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) {
            binding.url = url
            binding.executePendingBindings()
        }
    }
}