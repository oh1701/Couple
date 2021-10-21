package com.project.myapplication.di

import com.project.myapplication.MainRepository
import com.project.myapplication.MainViewModel
import com.project.myapplication.common.PhotoFilePath
import com.project.myapplication.intro.viewmodel.IntroViewModel
import com.project.myapplication.start.StartViewModel
import com.project.myapplication.start.StartRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class MyModules{

}
val koinViewModelModule = module {
    viewModel { MainViewModel(MainRepository()) }
    viewModel { IntroViewModel() }
    viewModel { StartViewModel(StartRepository()) }
}

val koinSingleModule = module {
    single { PhotoFilePath(get()) }
}

val koinFactoryModule = module {
}
