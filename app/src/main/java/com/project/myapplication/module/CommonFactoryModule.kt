package com.project.myapplication.module

import com.project.myapplication.common.CustomDialog
import org.koin.dsl.module

val commonFactoryModule = module{
    factory { CustomDialog(get()) }
}