package com.moondroid.project01_meetingapp.ui.view.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ItemHomeCategoryBinding
import com.moondroid.project01_meetingapp.databinding.ItemHomeGroupInfoBinding
import com.moondroid.project01_meetingapp.databinding.ItemInterestBinding
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.utils.Constants
import com.moondroid.project01_meetingapp.utils.DMUtils
import java.util.*
import kotlin.collections.ArrayList

class InterestAdapter(
    private val context: BaseActivity
) : RecyclerView.Adapter<InterestAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemInterestBinding.inflate(LayoutInflater.from(context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()

        holder.itemView.setOnClickListener {
            val intent = Intent()
            intent.putExtra(Constants.IntentParam.INTEREST, DMUtils.getStringId(context, String.format("interest_%02d", position + 1 )))
            intent.putExtra(Constants.IntentParam.INTEREST_ICON, DMUtils.getDrawableId(context, String.format("ic_interest_%02d", position + 1)))
            context.setResult(Activity.RESULT_OK, intent)
            context.finish()
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