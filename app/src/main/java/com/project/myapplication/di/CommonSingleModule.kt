package com.project.myapplication.di

import com.project.myapplication.data.room.db.RoomDiaryDB
import com.project.myapplication.data.sharedpreference.IntroSettingShared
import com.project.myapplication.utils.DialogSettings
import com.project.myapplication.utils.GeoCoder
import org.koin.dsl.module

val commonSingleModule = module {
    single { RoomDiaryDB.INSTANCE!!.roomDaoImage() }
    single { RoomDiaryDB.INSTANCE!!.roomDaoCoupleSetting() }
    single { RoomDiaryDB.INSTANCE!!.roomDaoFont() }
    single { IntroSettingShared(get()) }
    single { DialogSettings(get()) }
    single { GeoCoder(get()) }
}