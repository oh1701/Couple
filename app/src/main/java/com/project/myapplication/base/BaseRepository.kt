package com.project.myapplication.base

import com.project.myapplication.data.dao.RoomCoupleSettingDao
import com.project.myapplication.data.dao.RoomDiaryDao
import com.project.myapplication.data.db.RoomDiaryDB
import org.koin.core.KoinComponent
import org.koin.core.inject

open class BaseRepository:KoinComponent{
    protected open val roomDiaryDao:RoomDiaryDao by inject()
    protected open val roomDaoCoupleSetting:RoomCoupleSettingDao by inject()
}