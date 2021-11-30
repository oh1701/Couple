package com.project.myapplication.ui.start.repository

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import com.project.myapplication.base.BaseRepository
import com.project.myapplication.db.room.dao.RoomCoupleSettingDao
import com.project.myapplication.db.room.dao.RoomDiaryDao
import com.project.myapplication.db.room.entity.RoomCoupleSettingEntity
import com.project.myapplication.db.sharedpreference.IntroSettingShared
import io.reactivex.Single

class StartRepository:BaseRepository() {
    override val roomDiaryDao: RoomDiaryDao
        get() = super.roomDiaryDao
    override val roomDaoCoupleSetting: RoomCoupleSettingDao
        get() = super.roomDaoCoupleSetting
    override val sharedPreferences: IntroSettingShared
        get() = super.sharedPreferences

    override fun getCoupleDate(): Long {
        return super.getCoupleDate()
    }

    fun getDateTime(): SpannableStringBuilder {
        val span = SpannableStringBuilder("우리 사랑\n${getCoupleDate()}일 째")
        span.setSpan(RelativeSizeSpan(0.5f), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return span
    }

    fun getCoupleSetting(): Single<List<RoomCoupleSettingEntity>> {
        return roomDaoCoupleSetting.selectAllDao()
    }
}