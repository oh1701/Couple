package com.project.myapplication.ui.travel.repository

import com.project.myapplication.base.BaseRepository
import com.project.myapplication.data.dao.RoomDiaryDao
import com.project.myapplication.data.entity.RoomDiaryEntity
import io.reactivex.Single

class TravelMapRepository:BaseRepository() {
    override val roomDao: RoomDiaryDao
        get() = super.roomDao

    fun getAllDiary(): Single<List<RoomDiaryEntity>> {
        return roomDao.selectAllDao()
    }
}