package com.moondroid.project01_meetingapp.utils.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.moondroid.project01_meetingapp.utils.DMUtils


@BindingAdapter("image")
fun ImageView.loadImage(imageURL: String?) {
    Glide.with(this)
        .load(imageURL?.let { DMUtils.getImgUrl(it) })
        .into(this)
}