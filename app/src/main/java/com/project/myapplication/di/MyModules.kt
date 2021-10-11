package com.project.myapplication.di

import com.project.myapplication.base.BaseRepository
import com.project.myapplication.base.BaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class MyModules{

}
val koinViewModelModule = module {
    viewModel { BaseViewModel<BaseRepository>() }
}

val koinFactoryModule = module{
    factory { BaseRepository() }
    factory { MyModules() }
}