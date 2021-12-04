package com.project.myapplication.base

import android.content.Context
import com.project.myapplication.data.room.dao.RoomCoupleSettingDao
import com.project.myapplication.data.room.dao.RoomDiaryDao
import com.project.myapplication.data.room.dao.RoomFontDao
import com.project.myapplication.data.sharedpreference.IntroSettingShared
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*

open class BaseRepository:KoinComponent{
    protected open val roomDiaryDao:RoomDiaryDao by inject()
    protected open val roomDaoCoupleSetting:RoomCoupleSettingDao by inject()
    protected open val roomFontDao:RoomFontDao by inject()
    protected open val sharedPreferences:IntroSettingShared by inject()

    protected open fun getCoupleDate(): Long {
        val coupleDate = sharedPreferences.getCoupleDate()
        val simpleDate = "${coupleDate[0]}-${coupleDate[1]}-${coupleDate[2]} 00:00:00"
        val sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val parseDate = sf.parse(simpleDate)

        val today = Calendar.getInstance()

        return (today.time.time - parseDate.time) / (60 * 60 * 24 * 1000)
    }
}