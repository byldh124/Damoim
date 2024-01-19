package com.moondroid.project01_meetingapp.presentation.dialog

import android.content.Context
import android.os.Bundle
import com.moondroid.project01_meetingapp.databinding.DialogDisconnectNetworkBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseDialog

class DisconnectNetworkDialog(context: Context) : BaseDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DialogDisconnectNetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}