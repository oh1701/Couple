package com.project.myapplication.ui.intro.repository

import com.project.myapplication.base.BaseRepository
import com.project.myapplication.data.room.dao.RoomCoupleSettingDao
import com.project.myapplication.data.room.dao.RoomDiaryDao
import com.project.myapplication.data.sharedpreference.IntroSettingShared

class IntroRepository:BaseRepository() {
    override val roomDiaryDao: RoomDiaryDao
        get() = super.roomDiaryDao
    override val roomDaoCoupleSetting: RoomCoupleSettingDao
        get() = super.roomDaoCoupleSetting
    override val sharedPreferences: IntroSettingShared
        get() = super.sharedPreferences

    fun checkCoupleDateSetting():Boolean{
        return sharedPreferences.getIntroCoupleDateSetting()
    }
}