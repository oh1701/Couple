package com.project.myapplication.ui.travel.repository

import android.text.Editable
import android.text.Html
import android.text.SpannableString
import android.text.SpannableStringBuilder
import androidx.core.text.toSpannable
import com.project.myapplication.base.BaseRepository
import com.project.myapplication.data.room.dao.FontDao
import com.project.myapplication.data.room.dao.RoomDiaryDao
import com.project.myapplication.data.room.db.RoomDiaryDB
import com.project.myapplication.data.room.entity.Font
import com.project.myapplication.data.room.entity.RoomDiaryEntity
import com.project.myapplication.utils.FontToHtml
import io.reactivex.Completable
import io.reactivex.Single

class TravelDiaryRepository:BaseRepository() {
    override val roomDiaryDao: RoomDiaryDao
        get() = super.roomDiaryDao
    val font:FontDao = RoomDiaryDB.INSTANCE!!.font()


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

    fun returnFontHtml(otherHtml: String):String{
        return otherHtml + FontToHtml().endHtml
    }
}