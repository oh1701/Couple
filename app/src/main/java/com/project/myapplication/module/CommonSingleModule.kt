package com.project.myapplication.module

import com.project.myapplication.common.PhotoFilePath
import com.project.myapplication.data.db.RoomDiaryDB
import com.project.myapplication.data.sharedpreference.IntroSettingShared
import org.koin.dsl.module

val commonSingleModule = module {
    single { PhotoFilePath(get()) }
    single { RoomDiaryDB.INSTANCE!!.roomDaoImage() }
    single { RoomDiaryDB.INSTANCE!!.roomDaoCoupleSetting() }
}