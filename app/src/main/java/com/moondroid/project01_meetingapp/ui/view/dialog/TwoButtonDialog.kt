package com.moondroid.project01_meetingapp.ui.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseDialog
import com.moondroid.project01_meetingapp.databinding.DialogTwoButtonBinding

class TwoButtonDialog(context: Context, var msg: String, var onClick: () -> Unit) :BaseDialog(context) {
    lateinit var binding: DialogTwoButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_two_button,
            null,
            false
        )
        setContentView(binding.root)
    }

    fun confirm() {
        dismiss()
        onClick()
    }

    override fun show() {
        super.show()
        binding.dialog = this
    }
}