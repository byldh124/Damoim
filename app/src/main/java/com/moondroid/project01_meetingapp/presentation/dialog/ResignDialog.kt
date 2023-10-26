package com.moondroid.project01_meetingapp.presentation.dialog

import android.content.Context
import android.os.Bundle
import com.moondroid.project01_meetingapp.databinding.DialogResignBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseDialog

class ResignDialog(context: Context, private val onResign: () -> Unit) : BaseDialog(context = context) {

    private lateinit var binding : DialogResignBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogResignBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}