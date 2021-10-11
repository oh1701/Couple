package com.project.myapplication.di

import com.project.myapplication.base.BaseRepository
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.intro.repository.IntroRepository
import com.project.myapplication.intro.viewmodel.IntroViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class MyModules{

}
val koinViewModelModule = module {
    viewModel { BaseViewModel() }
    viewModel { IntroViewModel(IntroRepository()) }
}

val koinFactoryModule = module {
}
