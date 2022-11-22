package com.moondroid.project01_meetingapp.presentation.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ItemHomeCategoryBinding
import com.moondroid.project01_meetingapp.utils.DMUtils
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class CategoryListAdapter(
    private val ctx: Context,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    private var checkedPosition: Int by Delegates.observable(0) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeCategoryBinding.inflate(LayoutInflater.from(ctx), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        val category: String =
            ctx.getString(DMUtils.getStringId(ctx, String.format("interest_%02d", position)))

        holder.container.setOnClickListener {
            listener.onClick(category)
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

            if (checkedPosition == adapterPosition) {
                binding.txtCategory.setTextColor(ContextCompat.getColor(ctx, R.color.red_light01))
            } else {
                binding.txtCategory.setTextColor(ContextCompat.getColor(ctx, R.color.gray_light01))
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(category: String)
    }
}