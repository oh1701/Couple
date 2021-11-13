package com.project.myapplication.ui.travel.repository

import android.util.Log
import com.project.myapplication.base.BaseRepository
import com.project.myapplication.data.dao.RoomCoupleSettingDao
import com.project.myapplication.data.dao.RoomDiaryDao
import com.project.myapplication.data.entity.RoomDiaryEntity
import io.reactivex.Completable
import io.reactivex.Scheduler
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
        return "+ 164일"
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