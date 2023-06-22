package com.moondroid.project01_meetingapp.presentation.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

open class BaseFragment(@LayoutRes val layoutId: Int) : Fragment(layoutId) {

}