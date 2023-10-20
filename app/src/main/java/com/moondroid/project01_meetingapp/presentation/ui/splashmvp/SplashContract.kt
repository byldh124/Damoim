package com.moondroid.project01_meetingapp.presentation.ui.splashmvp

import com.moondroid.project01_meetingapp.presentation.base.BaseActivity

class SplashContract {
    interface View: BaseView {
        fun update()
        fun <T: BaseActivity> screen(cls: Class<T>)
    }

    interface Presenter {
        fun checkAppVersion(code: Int, name: String, pckName: String)
    }
}

interface BaseView {
    fun error(throwable: Throwable)
    fun fail(code: Int)
}