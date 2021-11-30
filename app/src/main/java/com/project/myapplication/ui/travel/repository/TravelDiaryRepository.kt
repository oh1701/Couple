package com.project.myapplication.ui.travel.repository

import com.project.myapplication.base.BaseRepository
import com.project.myapplication.db.room.dao.RoomDiaryDao
import com.project.myapplication.db.room.dao.RoomFontDao
import com.project.myapplication.db.room.entity.RoomDiaryEntity
import com.project.myapplication.db.room.entity.RoomFontEntity
import io.reactivex.Completable
import io.reactivex.Single

class TravelDiaryRepository:BaseRepository() {
    override val roomDiaryDao: RoomDiaryDao
        get() = super.roomDiaryDao
    override val roomFontDao: RoomFontDao
        get() = super.roomFontDao

    fun insertDiaryDB(entity: RoomDiaryEntity): Completable {
        return if(entity.latitude != 0.0 && entity.longitude != 0.0) {
            roomDiaryDao.insertDao(entity)
        }
        else {
            Completable.error(Throwable("LatLng Null"))
        }
    }

    fun updateDiaryDB(entity: RoomDiaryEntity): Completable {
        return if(entity.latitude != 0.0 && entity.longitude != 0.0) {
            roomDiaryDao.insertDao(entity)
        }
        else {
            Completable.error(Throwable("LatLng Null"))
        }
    }

    fun selectDB(id:Int): Single<RoomDiaryEntity> {
        return roomDiaryDao.selectIdDao(id)
    }

    fun getDateday():String{
        return "+ ${getCoupleDate()}일"
    }

    fun getDiaryDB(): Single<List<RoomDiaryEntity>>{
        return roomDiaryDao.selectAllDao()
    }

    fun getDiaryID(id:Int):Single<RoomDiaryEntity>{
        return roomDiaryDao.selectIdDao(id)
    }

    fun insertFontDB(roomFontEntity: RoomFontEntity):Completable{
        return roomFontDao.insertDao(roomFontEntity)
    }

    fun updateFontDB(roomFontEntity: RoomFontEntity):Completable{
        return roomFontDao.updateDao(roomFontEntity)
    }
}