package com.moondroid.project01_meetingapp.ui.view.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.moondroid.project01_meetingapp.databinding.ItemLocationBinding
import com.moondroid.project01_meetingapp.model.Address
import com.moondroid.project01_meetingapp.ui.view.activity.LocationActivity
import com.moondroid.project01_meetingapp.utils.IntentParam
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class AddressAdapter(private val activity: LocationActivity) :
    RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    private var list: List<Address> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun updateList(newList: List<Address>) {
        list = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLocationBinding.inflate(LayoutInflater.from(activity))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val location = list[position]
            bind(location.address)
            itemView.setOnClickListener {
                val intent = Intent()
                intent.putExtra(IntentParam.ADDRESS, Gson().toJson(location))
                activity.setResult(Activity.RESULT_OK, intent)
                activity.finish()
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