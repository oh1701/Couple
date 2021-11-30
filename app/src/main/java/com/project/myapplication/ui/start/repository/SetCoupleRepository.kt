package com.project.myapplication.ui.start.repository

import com.project.myapplication.base.BaseRepository
import com.project.myapplication.db.room.dao.RoomCoupleSettingDao
import com.project.myapplication.db.room.entity.RoomCoupleSettingEntity
import io.reactivex.Completable
import io.reactivex.Single

class SetCoupleRepository:BaseRepository() {
    override val roomDaoCoupleSetting: RoomCoupleSettingDao
        get() = super.roomDaoCoupleSetting

    fun insertCoupleSetting(insert:RoomCoupleSettingEntity):Completable{
        return roomDaoCoupleSetting.insertDao(insert)
    }

    fun updateCoupleSetting(update:RoomCoupleSettingEntity):Completable{
        return roomDaoCoupleSetting.updateDao(update)
    }

    fun getCoupleSetting(id:Int): Single<RoomCoupleSettingEntity> {
        return roomDaoCoupleSetting.selectIdDao(id)
    }
}