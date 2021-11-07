package com.project.myapplication.ui.travel.repository

import com.project.myapplication.base.BaseRepository
import com.project.myapplication.data.dao.RoomCoupleSettingDao
import com.project.myapplication.data.dao.RoomDiaryDao
import com.project.myapplication.data.entity.RoomDiaryEntity
import io.reactivex.Single

class TravelMapRepository:BaseRepository() {
    override val roomDaoImage: RoomDiaryDao
        get() = super.roomDaoImage
    override val roomDaoCoupleSetting: RoomCoupleSettingDao
        get() = super.roomDaoCoupleSetting

    fun getAllDiary(): Single<List<RoomDiaryEntity>> {
        return roomDaoImage.selectAllDao()
    }
}