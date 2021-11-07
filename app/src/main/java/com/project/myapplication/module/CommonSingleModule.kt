package com.project.myapplication.module

import com.project.myapplication.common.CheckSelfPermission
import com.project.myapplication.common.PhotoFilePath
import com.project.myapplication.data.dao.RoomDiaryDao
import com.project.myapplication.data.db.RoomDiaryDB
import org.koin.dsl.module

class CommonSingleModule {
}

val commonSingleModule = module {
    single { PhotoFilePath(get()) }
    single { RoomDiaryDB.INSTANCE!!.roomDaoImage() }
    single { RoomDiaryDB.INSTANCE!!.roomDaoCoupleSetting() }
}