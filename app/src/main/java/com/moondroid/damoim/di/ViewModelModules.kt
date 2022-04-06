package com.moondroid.damoim.di

import com.moondroid.damoim.ui.viewmodel.GroupViewModel
import com.moondroid.damoim.ui.viewmodel.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {

    /*splashScreen*/

    /*Home*/
    viewModel { HomeViewModel(repository = get()) }

    /*Group*/
    viewModel { GroupViewModel(repository = get()) }

}
