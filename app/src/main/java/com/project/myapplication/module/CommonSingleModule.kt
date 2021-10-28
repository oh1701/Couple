package com.project.myapplication.module

import com.project.myapplication.common.CheckSelfPermission
import com.project.myapplication.common.PhotoFilePath
import org.koin.dsl.module

class CommonSingleModule {
}

val commonSingleModule = module {
    single { PhotoFilePath(get()) }
}