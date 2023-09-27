package com.moondroid.project01_meetingapp.presentation.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.presentation.base.BaseDialog
import com.moondroid.project01_meetingapp.databinding.DialogLoadingBinding
import com.moondroid.project01_meetingapp.utils.ViewExtension.glide

class LoadingDialog(context: Context) : BaseDialog(context) {
    private lateinit var binding: DialogLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLoadingBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        setCanceledOnTouchOutside(false)
        binding.imgLoading.glide(R.drawable.loading_black)
    }
}