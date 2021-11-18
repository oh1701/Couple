package com.project.myapplication.di

import com.project.myapplication.ui.dialog.view.WarningDialogFragment
import com.project.myapplication.utils.Datepicker
import com.project.myapplication.utils.PhotoClass
import org.koin.dsl.module

val commonFactoryModule = module{
    factory { WarningDialogFragment() }
    factory { PhotoClass(get()) }
}