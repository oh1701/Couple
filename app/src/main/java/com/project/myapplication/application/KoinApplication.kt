package com.project.myapplication.application

import android.app.Application
import android.util.Log
import com.project.myapplication.data.room.db.RoomDiaryDB
import com.project.myapplication.di.commonFactoryModule
import com.project.myapplication.di.commonSingleModule
import com.project.myapplication.di.viewModelModule
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@KoinApplication)
            modules(viewModelModule, commonSingleModule, commonFactoryModule)
        }

        RxJavaPlugins.setErrorHandler { Log.e("RX JAVA Error::", it.toString()) }
        RoomDiaryDB.getInstance(applicationContext)
    }
}