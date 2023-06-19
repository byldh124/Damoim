package com.moondroid.project01_meetingapp.presentation.ui.location

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.project01_meetingapp.databinding.ItemLocationBinding
import com.moondroid.project01_meetingapp.domain.model.Address
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class AddressAdapter(private val onClick : (Address) -> Unit) :
    RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    private var list: List<Address> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun updateList(newList: List<Address>) {
        list = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val location = list[position]
            bind(location.address)
            itemView.setOnClickListener {
                onClick(location)
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