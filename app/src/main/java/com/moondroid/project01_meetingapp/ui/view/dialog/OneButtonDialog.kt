package com.moondroid.project01_meetingapp.ui.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseDialog
import com.moondroid.project01_meetingapp.databinding.DialogOneButtonBinding

class OneButtonDialog(context: Context, var msg: String, var onClick: () -> Unit) :
    BaseDialog(context) {

    lateinit var binding: DialogOneButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_one_button,
            null,
            false
        )
        setContentView(binding.root)
    }

    fun confirm() {
        cancel()
    }

    override fun show() {
        super.show()
        binding.dialog = this
    }

    override fun cancel() {
        super.cancel()
        onClick()
    }
}