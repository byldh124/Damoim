package com.moondroid.project01_meetingapp.presentation.base

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

open class BaseFragment(@LayoutRes val layoutId: Int) : Fragment(layoutId) {
    private var onResult: (Intent?) -> Unit = {}
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                onResult(result.data)
            }
        }

    protected fun startActivityForResult(intent: Intent, onResult: (Intent?) -> Unit) {
        this.onResult = onResult
        launcher.launch(intent)
    }
}