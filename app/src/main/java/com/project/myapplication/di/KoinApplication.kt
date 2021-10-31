package com.project.myapplication.di

import android.app.Application
import com.project.myapplication.data.db.RoomImageDB
import com.project.myapplication.module.commonSingleModule
import com.project.myapplication.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@KoinApplication)
            modules(viewModelModule, commonSingleModule)
        }

        RoomImageDB.getInstance(applicationContext)
    }
}