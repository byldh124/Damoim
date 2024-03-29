package com.moondroid.project01_meetingapp.utils

import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.gms.common.SignInButton
import com.moondroid.damoim.common.GroupType
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.presentation.widget.DMRecycler
import com.moondroid.project01_meetingapp.utils.ViewExtension.getDrawableId
import com.moondroid.project01_meetingapp.utils.ViewExtension.getInterestNum
import com.moondroid.project01_meetingapp.utils.ViewExtension.getStringId
import com.moondroid.project01_meetingapp.utils.ViewExtension.glide
import com.moondroid.project01_meetingapp.utils.ViewExtension.visible

object BindingAdapter {
    @BindingAdapter("visible")
    @JvmStatic
    fun View.setVisible(boolean: Boolean) {
        visible(boolean)
    }

    @BindingAdapter("image")
    @JvmStatic
    fun ImageView.loadImage(imageURL: String) {
        glide(imageURL)
    }

    @BindingAdapter("favor")
    @JvmStatic
    fun ImageView.setFavorImage(favor: Boolean) {
        if (favor) {
            glide(R.drawable.ic_favorite)
        } else {
            glide(R.drawable.ic_favorite_not)
        }
    }

    @BindingAdapter("isSelect")
    @JvmStatic
    fun AppCompatTextView.setSelectedBackground(b: Boolean) {
        if (b) {
            setTextColor(ContextCompat.getColor(context, R.color.red_light01))
        } else {
            setTextColor(ContextCompat.getColor(context, R.color.gray_light01))
        }
    }


    /**
     * 관심사 문자열로 관심사 아이콘 로드
     * @param interest (아웃도어/여행 , 운동/스포츠 등등..)
     */
    @BindingAdapter("interest")
    @JvmStatic
    fun ImageView.interest(interest: String) {
        val position = getInterestNum(context, interest)

        val name = String.format("ic_interest_%02d", position)

        glide(getDrawableId(context, name))
    }

    /**
     * Load interest icon in ImageView about RecyclerView position
     * @param position position by recyclerView adapter
     */
    @BindingAdapter("interestIcon")
    @JvmStatic
    fun ImageView.interestIcon(position: Int) {
        val name = String.format("ic_interest_%02d", position)
        glide(getDrawableId(context, name))
    }

    /**
     * Set interest text in TextView about RecyclerView position
     * @param position position by recyclerView adapter
     */
    @BindingAdapter("interestText")
    @JvmStatic
    fun TextView.interestText(position: Int) {
        val name = String.format("interest_%02d", position)
        this.text = context.getString(getStringId(context, name))
    }

    @BindingAdapter("loadUrl")
    @JvmStatic
    fun WebView.webLoad(url: String) {
        this.loadUrl(url)
    }

    /**
     * Google SignInButton is not support DataBinding
     **/
    @BindingAdapter("onClick")
    @JvmStatic
    fun SignInButton.onClick(method: () -> Unit) {
        this.setOnClickListener { method.invoke() }
    }

    @BindingAdapter("groupListTitle")
    @JvmStatic
    fun TextView.setGroupTitle(type: GroupType) {
        text = when (type) {
            GroupType.FAVORITE -> context.getString(R.string.title_group_list_favorite)
            GroupType.RECENT -> context.getString(R.string.title_group_list_recent)
            GroupType.MY_GROUP -> context.getString(R.string.cmn_my_group)
            GroupType.ALL -> "전체"
        }
    }

    @BindingAdapter("emptyMessage")
    @JvmStatic
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

            GroupType.ALL -> setEmptyText(
                "현재 생성된 모임이 없습니다."
            )
        }
    }
}
