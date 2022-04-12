package com.moondroid.damoim.ui.view.dialog

import android.content.Context
import android.os.Bundle
import com.moondroid.damoim.R
import com.moondroid.damoim.base.BaseDialog

class LoadingDialog(context:Context) : BaseDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
    }
}