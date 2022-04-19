package com.moondroid.damoim.utils.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.moondroid.damoim.utils.DMLog
import com.moondroid.damoim.utils.DMUtils


@BindingAdapter("image")
fun ImageView.loadImage(imageURL: String?) {
    Glide.with(this)
        .load(imageURL?.let { DMUtils.getImgUrl(it) })
        .into(this)
}