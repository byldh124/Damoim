package com.moondroid.project01_meetingapp.utils

import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.gms.common.SignInButton
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.presentation.view.grouplist.GroupListActivity
import com.moondroid.project01_meetingapp.presentation.widget.DMRecycler

@BindingAdapter("visible")
fun View.visible(boolean: Boolean){
    if (boolean) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

@BindingAdapter("image")
fun ImageView.loadImage(imageURL: String?) {
    Glide.with(this)
        .load(imageURL?.let { DMUtils.getImgUrl(it) })
        .into(this)
}

/**
 * 관심사 문자열로 관심사 아이콘 로드
 * @param interest (아웃도어/여행 , 운동/스포츠 등등..)
 */
@BindingAdapter("interest")
fun ImageView.interest(interest: String) {
    val position = DMUtils.getInterestNum(context, interest)

    val name = String.format("ic_interest_%02d", position)

    Glide.with(this)
        .load(DMUtils.getDrawableId(context, name))
        .into(this)
}

/**
 * Load interest icon in ImageView about RecyclerView position
 * @param position position by recyclerView adapter
 */
@BindingAdapter("interestIcon")
fun ImageView.interestIcon(position: Int) {
    val name = String.format("ic_interest_%02d", position)

    Glide.with(this)
        .load(DMUtils.getDrawableId(context, name))
        .into(this)
}

/**
 * Set interest text in TextView about RecyclerView position
 * @param position position by recyclerView adapter
 */
@BindingAdapter("interestText")
fun TextView.interestText(position: Int) {
    val name = String.format("interest_%02d", position)

    this.text = context.getString(DMUtils.getStringId(context, name))
}

@BindingAdapter("loadUrl")
fun WebView.webLoad(url: String) {
    this.loadUrl(url)
}

/**
 * Google SignInButton is not support DataBinding
 **/
@BindingAdapter("onClick")
fun SignInButton.onClick(method: () -> Unit) {
    this.setOnClickListener { method.invoke() }
}

@BindingAdapter("groupListTitle")
fun TextView.setGroupTitle(type: GroupType) {
    text = when (type) {
        GroupType.FAVORITE -> context.getString(R.string.title_group_list_favorite)
        GroupType.RECENT -> context.getString(R.string.title_group_list_recent)
        GroupType.MY_GROUP -> context.getString(R.string.cmn_my_group)
    }
}

@BindingAdapter("emptyMessage")
fun DMRecycler.setEmptyMessage(type: GroupType) {
    when (type) {
        GroupType.FAVORITE -> setEmptyText(
            context.getString(R.string.alm_favor_group_empty)
        )
        GroupType.RECENT -> setEmptyText(
            context.getString(R.string.alm_recent_group_empty)
        )
        GroupType.MY_GROUP -> setEmptyText(
            context.getString(R.string.alm_my_group_empty)
        )
    }
}