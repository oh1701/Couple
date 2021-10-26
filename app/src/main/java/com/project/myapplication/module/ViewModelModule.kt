package com.project.myapplication.module

import com.project.myapplication.views.MainRepository
import com.project.myapplication.views.MainViewModel
import com.project.myapplication.views.intro.viewmodel.IntroViewModel
import com.project.myapplication.views.start.StartRepository
import com.project.myapplication.views.start.StartViewModel
import com.project.myapplication.views.travel.viewmodel.TravelDiaryViewModel
import com.project.myapplication.views.travel.viewmodel.TravelMapViewModel
import com.project.myapplication.views.travel.viewmodel.TravelViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class ViewModelModule {
}

val viewModelModule = module {
    viewModel { MainViewModel(MainRepository()) }
    viewModel { IntroViewModel() }
    viewModel { StartViewModel(StartRepository()) }
    viewModel { TravelMapViewModel() }
    viewModel { TravelDiaryViewModel() }
    viewModel { TravelViewModel()}
}