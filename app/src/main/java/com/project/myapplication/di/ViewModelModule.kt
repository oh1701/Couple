package com.project.myapplication.di

import com.project.myapplication.ui.main.MainRepository
import com.project.myapplication.ui.main.MainViewModel
import com.project.myapplication.ui.dialog.repository.WarningDialogRepository
import com.project.myapplication.ui.dialog.viewmodel.WarningDialogViewModel
import com.project.myapplication.ui.intro.repository.FirstAccessSettingRepository
import com.project.myapplication.ui.intro.repository.IntroRepository
import com.project.myapplication.ui.intro.viewmodel.FirstAccessSettingViewModel
import com.project.myapplication.ui.intro.viewmodel.IntroViewModel
import com.project.myapplication.ui.start.repository.SetCoupleRepository
import com.project.myapplication.ui.start.repository.StartRepository
import com.project.myapplication.ui.start.viewmodel.SetCoupleViewModel
import com.project.myapplication.ui.start.viewmodel.StartViewModel
import com.project.myapplication.ui.travel.repository.TravelDiaryRepository
import com.project.myapplication.ui.travel.repository.TravelMapRepository
import com.project.myapplication.ui.travel.viewmodel.TravelDiaryViewModel
import com.project.myapplication.ui.travel.viewmodel.TravelMapViewModel
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(MainRepository()) }
    viewModel { IntroViewModel(IntroRepository()) }
    viewModel { StartViewModel(StartRepository()) }
    viewModel { TravelMapViewModel(TravelMapRepository())}
    viewModel { TravelDiaryViewModel(TravelDiaryRepository()) }
    viewModel { TravelViewModel()}
    viewModel { SetCoupleViewModel(SetCoupleRepository()) }
    viewModel { FirstAccessSettingViewModel(FirstAccessSettingRepository()) }
    viewModel { WarningDialogViewModel(WarningDialogRepository()) }
}