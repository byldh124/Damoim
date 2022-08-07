package com.moondroid.project01_meetingapp.di

import com.moondroid.project01_meetingapp.ui.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {

    /*splashScreen*/
    viewModel { SplashViewModel(repository = get()) }

    /*Home*/
    viewModel { HomeViewModel(repository = get()) }

    /*Group*/
    viewModel { GroupViewModel(repository = get()) }

    /*SignIn*/
    viewModel { SignInViewModel(repository = get()) }

    /*SignIn*/
    viewModel { SignUpViewModel(repository = get()) }

    /*Profile*/
    viewModel {ProfileViewModel(repository = get())}

}
