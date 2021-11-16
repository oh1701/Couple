package com.project.myapplication.di

import com.project.myapplication.ui.dialog.view.WarningDialogFragment
import org.koin.dsl.module

val commonFactoryModule = module{
    factory { WarningDialogFragment() }
}