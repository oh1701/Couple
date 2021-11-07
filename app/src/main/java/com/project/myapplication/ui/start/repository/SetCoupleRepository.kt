package com.project.myapplication.ui.start.repository

import android.widget.Toast
import com.project.myapplication.base.BaseRepository
import com.project.myapplication.data.dao.RoomCoupleSettingDao
import com.project.myapplication.data.dao.RoomDiaryDao
import com.project.myapplication.data.entity.RoomCoupleSettingEntity
import io.reactivex.Completable

class SetCoupleRepository:BaseRepository() {
    override val roomDaoCoupleSetting: RoomCoupleSettingDao
        get() = super.roomDaoCoupleSetting

    fun insertCoupleSetting(insert:RoomCoupleSettingEntity):Completable{
        return roomDaoCoupleSetting.insertDao(insert)
    }
}