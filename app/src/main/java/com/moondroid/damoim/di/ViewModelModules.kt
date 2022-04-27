package com.moondroid.damoim.di

import com.moondroid.damoim.ui.viewmodel.*
import org.koin.android.viewmodel.dsl.viewModel
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

}
