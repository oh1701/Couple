package com.project.myapplication.ui.intro.repository

import com.project.myapplication.base.BaseRepository
import com.project.myapplication.db.room.dao.RoomCoupleSettingDao
import com.project.myapplication.db.room.dao.RoomDiaryDao
import com.project.myapplication.db.sharedpreference.IntroSettingShared

class FirstAccessSettingRepository:BaseRepository() {
    override val roomDiaryDao: RoomDiaryDao
        get() = super.roomDiaryDao
    override val roomDaoCoupleSetting: RoomCoupleSettingDao
        get() = super.roomDaoCoupleSetting
    override val sharedPreferences: IntroSettingShared
        get() = super.sharedPreferences

    fun saveSharedCoupleDate(saveCoupleDate:List<Int?>){
        sharedPreferences.setIntroCoupleDateSetting(true) // 재설정시 메뉴 창에서 설정해달라는 말하기.
        sharedPreferences.setCoupleDate(saveCoupleDate)  // 커플 된 기간 넣어주기.
    }
}