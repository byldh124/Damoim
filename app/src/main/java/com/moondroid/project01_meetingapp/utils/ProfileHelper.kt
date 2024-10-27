package com.moondroid.project01_meetingapp.utils

import com.moondroid.damoim.domain.model.Profile

val tester = arrayOf("gtest01")

object ProfileHelper {
    lateinit var profile: Profile

    fun setProfile() = ::profile.isInitialized

    fun isTester() = tester.contains(profile.id)
}

