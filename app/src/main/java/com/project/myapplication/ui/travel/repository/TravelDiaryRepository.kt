package com.project.myapplication.ui.travel.repository

import com.project.myapplication.base.BaseRepository
import com.project.myapplication.data.dao.RoomDiaryDao
import com.project.myapplication.data.entity.RoomDiaryEntity
import io.reactivex.Completable
import io.reactivex.Single

class TravelDiaryRepository:BaseRepository() {
    override val roomDao: RoomDiaryDao
        get() = super.roomDao

    fun insertDB(entity: RoomDiaryEntity): Completable {
        return roomDao.insertDao(entity)
    }

    fun selectDB(id:Int): Single<RoomDiaryEntity> {
        return roomDao.selectIdDao(id)
    }

    fun getDateday():String{
        return "+ 164"
    }
}