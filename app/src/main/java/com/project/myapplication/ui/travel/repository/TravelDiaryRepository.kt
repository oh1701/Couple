package com.project.myapplication.ui.travel.repository

import com.project.myapplication.base.BaseRepository
import com.project.myapplication.data.dao.RoomDaoImage
import com.project.myapplication.data.entity.RoomEntityImage
import io.reactivex.Completable
import io.reactivex.Single

class TravelDiaryRepository:BaseRepository() {
    override val roomDao: RoomDaoImage
        get() = super.roomDao

    fun insertDB(entity: RoomEntityImage): Completable {
        return roomDao.insertDao(entity)
    }

    fun selectDB(title:String): Single<RoomEntityImage> {
        return roomDao.selectIdDao(title)
    }
}