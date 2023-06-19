package com.moondroid.project01_meetingapp.presentation.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.presentation.base.BaseDialog
import com.moondroid.project01_meetingapp.databinding.DialogOneButtonBinding

class OneButtonDialog(context: Context, var msg: String, var onClick: () -> Unit) : BaseDialog(context) {

    private lateinit var binding: DialogOneButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogOneButtonBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        binding.btnConfirm.setOnClickListener {
            cancel()
        }
    }

    override fun show() {
        super.show()
        binding.tvMessage.text = msg
    }

    override fun cancel() {
        super.cancel()
        onClick()
    }
}