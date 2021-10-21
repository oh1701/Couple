package com.project.myapplication.di

import android.app.Activity
import com.project.myapplication.MainRepository
import com.project.myapplication.MainViewModel
import com.project.myapplication.common.PermissionCheck
import com.project.myapplication.data.PhotoFilePath
import com.project.myapplication.intro.viewmodel.IntroViewModel
import com.project.myapplication.travel.FragementViewModel
import com.project.myapplication.travel.FragmentRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class MyModules{

}
val koinViewModelModule = module {
    viewModel { MainViewModel(MainRepository()) }
    viewModel { IntroViewModel() }
    viewModel { FragementViewModel(FragmentRepository()) }
}

val koinSingleModule = module {
    single { PhotoFilePath(get())}
}

val koinFactoryModule = module {
}
