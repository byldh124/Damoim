package com.moondroid.project01_meetingapp.utils.view

import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.utils.DMUtils


@BindingAdapter("image")
fun ImageView.loadImage(imageURL: String?) {
    Glide.with(this)
        .load(imageURL?.let { DMUtils.getImgUrl(it) })
        .into(this)
}

@BindingAdapter("interest")
fun ImageView.interest(interest: String) {
    val position = DMUtils.getInterestNum(context, interest)

    val name = String.format("ic_interest_%02d", position)

    Glide.with(this)
        .load(DMUtils.getDrawableId(context, name))
        .into(this)
}

@BindingAdapter("interestIcon")
fun ImageView.interestIcon(position: Int) {
    val name = String.format("ic_interest_%02d", position)

    Glide.with(this)
        .load(DMUtils.getDrawableId(context, name))
        .into(this)
}

@BindingAdapter("interestText")
fun TextView.interestText(position: Int) {
    val name = String.format("interest_%02d", position)

    this.text = context.getString(DMUtils.getStringId(context, name));
}

@BindingAdapter("loadUrl")
fun WebView.webLoad(url: String) {
    this.loadUrl(url)
}