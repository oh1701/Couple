package com.project.myapplication.ui.travel.repository

import com.project.myapplication.base.BaseRepository
import com.project.myapplication.data.dao.RoomDaoImage
import com.project.myapplication.data.entity.RoomEntityImage

class TravelDiaryRepository:BaseRepository() {
    override val roomDao: RoomDaoImage
        get() = super.roomDao

    fun insertDB(){

        roomDao.insertDao(RoomEntityImage(0, "1414", "1414", 1313))
    }
}