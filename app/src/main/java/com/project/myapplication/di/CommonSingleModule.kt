package com.project.myapplication.di

import com.project.myapplication.utils.PhotoFilePath
import com.project.myapplication.data.room.db.RoomDiaryDB
import com.project.myapplication.data.sharedpreference.IntroSettingShared
import org.koin.dsl.module

val commonSingleModule = module {
    single { PhotoFilePath(get()) }
    single { RoomDiaryDB.INSTANCE!!.roomDaoImage() }
    single { RoomDiaryDB.INSTANCE!!.roomDaoCoupleSetting() }
    single { IntroSettingShared(get()) }
}