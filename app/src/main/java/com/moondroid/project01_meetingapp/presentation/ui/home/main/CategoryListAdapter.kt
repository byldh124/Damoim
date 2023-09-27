package com.moondroid.project01_meetingapp.presentation.ui.home.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ItemHomeCategoryBinding
import com.moondroid.project01_meetingapp.utils.ViewExtension.getStringId
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class CategoryListAdapter (
    private val onClick : (String) -> Unit
) :
    RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    private var checkedPosition: Int by Delegates.observable(0) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val ctx = holder.itemView.context
        val category: String =
            ctx.getString(getStringId(ctx, String.format("interest_%02d", position)))

        holder.container.setOnClickListener {
            onClick(category)
            checkedPosition = position
        }

        holder.bind()
    }

    override fun getItemCount(): Int {
        return 20
    }

    inner class ViewHolder(
        private val binding: ItemHomeCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val container: ConstraintLayout = binding.container

        fun bind() {
            binding.position = adapterPosition
            binding.executePendingBindings()
            val ctx = itemView.context
            if (checkedPosition == adapterPosition) {
                binding.txtCategory.setTextColor(ContextCompat.getColor(ctx, R.color.red_light01))
            } else {
                binding.txtCategory.setTextColor(ContextCompat.getColor(ctx, R.color.gray_light01))
            }
        }
    }
}