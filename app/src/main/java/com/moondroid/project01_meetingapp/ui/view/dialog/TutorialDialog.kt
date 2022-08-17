package com.moondroid.project01_meetingapp.ui.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import androidx.databinding.DataBindingUtil
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseDialog
import com.moondroid.project01_meetingapp.databinding.DialogTutorialBinding

class TutorialDialog(context: Context): BaseDialog(context) {


    lateinit var binding: DialogTutorialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_tutorial, null, false)
        binding.dialog = this
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.amin_scale_up)
        binding.icSetting.animation = animation
        animation.start()

        val layoutParams = binding.header.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.topMargin = getStatusBarHeight()
    }

    private fun getStatusBarHeight():Int {
        var result = 0;
        val id = context.resources.getIdentifier("status_bar_height", "dimen", "android");
        if (id > 0) {
            result = context.resources.getDimensionPixelSize(id)
        }

        return result
    }

    fun userClick(@Suppress("UNUSED_PARAMETER")vw: View){
        cancel()
    }
}