package com.project.myapplication.ui.travel.repository

import com.project.myapplication.base.BaseRepository
import com.project.myapplication.data.room.dao.RoomCoupleSettingDao
import com.project.myapplication.data.room.dao.RoomDiaryDao
import com.project.myapplication.data.room.entity.RoomDiaryEntity
import io.reactivex.Completable
import io.reactivex.Single

class TravelMapRepository:BaseRepository() {
    override val roomDiaryDao: RoomDiaryDao
        get() = super.roomDiaryDao
    override val roomDaoCoupleSetting: RoomCoupleSettingDao
        get() = super.roomDaoCoupleSetting

    fun getAllDiary(): Single<List<RoomDiaryEntity>> {
        return roomDiaryDao.selectAllDao()
    }

    fun getIdDiary(id: Int):Single<RoomDiaryEntity>{
        return roomDiaryDao.selectIdDao(id)
    }
}