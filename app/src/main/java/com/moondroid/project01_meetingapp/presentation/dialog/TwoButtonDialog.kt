package com.moondroid.project01_meetingapp.presentation.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.presentation.base.BaseDialog
import com.moondroid.project01_meetingapp.databinding.DialogTwoButtonBinding

class TwoButtonDialog(context: Context, var msg: String, var listener: EventListener) : BaseDialog(context) {
    lateinit var binding: DialogTwoButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogTwoButtonBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
    }

    override fun show() {
        super.show()
        binding.listener = listener
        binding.tvMessage.text = msg
    }

    interface EventListener {
        fun onConfirm()
        fun onCancel()
    }
}