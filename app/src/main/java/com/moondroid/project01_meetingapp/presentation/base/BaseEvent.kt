package com.moondroid.project01_meetingapp.presentation.base

import com.moondroid.project01_meetingapp.presentation.ui.splash.SplashViewModel

sealed class BaseEvent {
    data class Fail(val code: Int)
    data class NetworkError(val throwable: Throwable)
}