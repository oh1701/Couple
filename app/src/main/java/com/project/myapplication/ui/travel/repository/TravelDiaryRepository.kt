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
        return roomDiaryDao.insertDao(entity)
    }

    fun selectDB(id:Int): Single<RoomDiaryEntity> {
        return roomDiaryDao.selectIdDao(id)
    }

    fun getDateday():String{
        return "+ ${getCoupleDate()}일"
    }

    fun getDBsize(): Int{
        var dbSize = 0

        roomDiaryDao
            .selectAllDao()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { dbSize = it.size
                Log.e("getDBsize ::", "사이즈는 ${it.size + 1},\n 내용물은 $it")
            }
            .doOnError { Log.e("실패", "실패") }
            .subscribe()

        return dbSize + 1
    }
}