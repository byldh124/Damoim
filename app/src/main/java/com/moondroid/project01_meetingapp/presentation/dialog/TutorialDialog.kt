package com.moondroid.project01_meetingapp.presentation.dialog

import android.content.Context
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.presentation.base.BaseDialog
import com.moondroid.project01_meetingapp.databinding.DialogTutorialBinding
import com.moondroid.project01_meetingapp.utils.ViewExtension.getStatusBarHeight

class TutorialDialog(context: Context) : BaseDialog(context) {
    private lateinit var binding: DialogTutorialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogTutorialBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        binding.root.setOnClickListener { cancel() }

        initView()
    }

    private fun initView() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.amin_scale_up)
        binding.icSetting.animation = animation
        animation.start()

        val layoutParams = binding.header.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.topMargin = getStatusBarHeight(context)
    }
}