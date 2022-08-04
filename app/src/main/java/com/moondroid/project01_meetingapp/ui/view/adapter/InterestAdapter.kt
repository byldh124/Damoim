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
import java.util.*
import kotlin.collections.ArrayList

class InterestAdapter(
    private val context: BaseActivity
) : RecyclerView.Adapter<InterestAdapter.ViewHolder>() {

    var imgUrls: Array<String>
    var desc: Array<String>

    init {
        val resources = context.resources
        imgUrls = resources.getStringArray(R.array.interest_icon_img_url)
        desc = resources.getStringArray(R.array.interest_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemInterestBinding.inflate(LayoutInflater.from(context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imgUrls[position], desc[position])

        holder.itemView.setOnClickListener {
            val intent = Intent()
            intent.putExtra(Constants.IntentParam.INTEREST, desc[position])
            context.setResult(Activity.RESULT_OK, intent)
            context.finish()
        }
    }

    override fun getItemCount(): Int {
        return imgUrls.size
    }

    inner class ViewHolder(
        private val binding: ItemInterestBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String, title: String) {
            binding.url = url
            binding.title = title
            binding.executePendingBindings()
        }
    }
}