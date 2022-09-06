package com.moondroid.project01_meetingapp.ui.view.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.project01_meetingapp.databinding.ItemInterestBinding
import com.moondroid.project01_meetingapp.ui.view.activity.InterestActivity
import com.moondroid.project01_meetingapp.utils.DMUtils
import com.moondroid.project01_meetingapp.utils.IntentParam

class InterestAdapter(
    private val activity: InterestActivity
) : RecyclerView.Adapter<InterestAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemInterestBinding.inflate(LayoutInflater.from(activity))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()

        holder.itemView.setOnClickListener {
            val intent = Intent()
            intent.putExtra(IntentParam.INTEREST, DMUtils.getStringId(activity, String.format("interest_%02d", position + 1 )))
            intent.putExtra(IntentParam.INTEREST_ICON, DMUtils.getDrawableId(activity, String.format("ic_interest_%02d", position + 1)))
            activity.setResult(Activity.RESULT_OK, intent)
            activity.finish()
        }
    }

    override fun getItemCount(): Int {
        return 19
    }

    inner class ViewHolder(
        private val binding: ItemInterestBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.position = adapterPosition + 1
            binding.executePendingBindings()
        }
    }
}