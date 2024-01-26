package com.moondroid.project01_meetingapp.presentation.ui.group.main.gallery

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.project01_meetingapp.databinding.ItemGalleryBinding
import com.moondroid.project01_meetingapp.presentation.dialog.GalleryDialog
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class GalleryAdapter(
    private val context: Context
) : ListAdapter<String, GalleryAdapter.ViewHolder>(StringDifferCallBack) {
    private var galleryDialog: GalleryDialog? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGalleryBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }


    override fun submitList(list: MutableList<String>?) {
        super.submitList(list)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            if (galleryDialog == null) {
                galleryDialog = GalleryDialog(context, currentList)
            }

            galleryDialog?.let { dialog ->
                dialog.position = position
                dialog.show()
            }
        }
    }

    inner class ViewHolder(
        private val binding: ItemGalleryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) {
            binding.url = url
            binding.executePendingBindings()
        }
    }

    object StringDifferCallBack : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}