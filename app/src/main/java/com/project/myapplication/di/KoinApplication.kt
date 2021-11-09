package com.project.myapplication.di

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.project.myapplication.data.db.RoomDiaryDB
import com.project.myapplication.data.sharedpreference.IntroSettingShared
import com.project.myapplication.module.commonSingleModule
import com.project.myapplication.module.viewModelModule
import com.project.myapplication.ui.travel.view.TravelMapFragment
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin

class KoinApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@KoinApplication)
            modules(viewModelModule, commonSingleModule)
        }

        RxJavaPlugins.setErrorHandler { Log.e("RX JAVA Error::", it.toString()) }
        RoomDiaryDB.getInstance(applicationContext)
        sharedPreference = IntroSettingShared(applicationContext)
    }

    companion object{
        lateinit var sharedPreference: IntroSettingShared
    }
}