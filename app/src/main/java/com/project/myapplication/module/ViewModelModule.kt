package com.project.myapplication.module

import com.project.myapplication.ui.MainRepository
import com.project.myapplication.ui.MainViewModel
import com.project.myapplication.ui.intro.viewmodel.IntroViewModel
import com.project.myapplication.ui.start.StartRepository
import com.project.myapplication.ui.start.StartViewModel
import com.project.myapplication.ui.travel.repository.TravelDiaryRepository
import com.project.myapplication.ui.travel.viewmodel.TravelDiaryViewModel
import com.project.myapplication.ui.travel.viewmodel.TravelMapViewModel
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class ViewModelModule {
}

val viewModelModule = module {
    viewModel { MainViewModel(MainRepository()) }
    viewModel { IntroViewModel() }
    viewModel { StartViewModel(StartRepository()) }
    viewModel { TravelMapViewModel()}
    viewModel { TravelDiaryViewModel(TravelDiaryRepository()) }
    viewModel { TravelViewModel()}
}