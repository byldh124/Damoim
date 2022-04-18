package com.moondroid.damoim.ui.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.damoim.R
import kotlinx.android.synthetic.main.item_home_category.view.*

@SuppressLint("NotifyDataSetChanged")
class CategoryListAdapter(
    private val ctx: Context,
    private val categories: ArrayList<String>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(ctx).inflate(R.layout.item_home_category, parent, false)
        )
    }

    fun updatePosition(){

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
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val container: ConstraintLayout = itemView.container
        private val category: TextView = itemView.txtCategory

        fun bind(categoryStr: String) {
            category.text = categoryStr
        }
    }

    interface OnItemClickListener {
        fun onClick(category: String)
    }
}