package com.moondroid.project01_meetingapp.ui.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseDialog
import com.moondroid.project01_meetingapp.databinding.DialogWebviewBinding

class WebViewDialog(context: Context, val type: TYPE) : BaseDialog(context) {

    lateinit var binding: DialogWebviewBinding

    enum class TYPE {
        USE_TERM,
        PRIVACY
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_webview,
            null,
            false
        )
        binding.dialog = this
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setType(type)
    }

    fun setType(type: TYPE) {
        when (type) {
            TYPE.USE_TERM -> {
                binding.tvUse.isEnabled = false
                binding.tvPrivacy.isEnabled = true

                binding.url = "http://moondroid.dothome.co.kr/UseTerm/DamoimMobile/useTerm.html"
            }
            TYPE.PRIVACY -> {
                binding.tvUse.isEnabled = true
                binding.tvPrivacy.isEnabled = false

                binding.url = "http://moondroid.dothome.co.kr/Privacy/damoimMobile/privacy.html"
            }
        }
    }

    fun exit(@Suppress("UNUSED_PARAMETER")vw: View) {
        cancel()
    }
}