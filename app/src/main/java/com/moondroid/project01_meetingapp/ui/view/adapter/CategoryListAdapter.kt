package com.moondroid.project01_meetingapp.ui.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.project01_meetingapp.databinding.ItemHomeCategoryBinding

@SuppressLint("NotifyDataSetChanged")
class CategoryListAdapter(
    private val ctx: Context,
    private val categories: ArrayList<String>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeCategoryBinding.inflate(LayoutInflater.from(ctx))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category: String = categories[position]
        holder.container.setOnClickListener {
            listener.onClick(category)
        }

        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class ViewHolder(
        private val binding: ItemHomeCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val container: ConstraintLayout = binding.container

        fun bind(categoryStr: String) {
            binding.category = categoryStr
            binding.executePendingBindings()
        }
    }

    interface OnItemClickListener {
        fun onClick(category: String)
    }
}