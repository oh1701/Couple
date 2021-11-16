package com.project.myapplication.ui.travel.repository

import android.util.Log
import com.project.myapplication.base.BaseRepository
import com.project.myapplication.data.room.dao.RoomDiaryDao
import com.project.myapplication.data.room.entity.RoomDiaryEntity
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TravelDiaryRepository:BaseRepository() {
    override val roomDiaryDao: RoomDiaryDao
        get() = super.roomDiaryDao


    fun insertDB(entity: RoomDiaryEntity): Completable {
        return if(entity.latitude != 0.0 && entity.longitude != 0.0) {
            roomDiaryDao.insertDao(entity)
        }
        else {
            Completable.error(Throwable("LatLng Null"))
        }
    }

    fun updateDB(entity: RoomDiaryEntity): Completable {
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
}