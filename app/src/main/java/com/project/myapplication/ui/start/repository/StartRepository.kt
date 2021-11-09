package com.project.myapplication.ui.start.repository

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import com.project.myapplication.base.BaseRepository
import com.project.myapplication.data.dao.RoomCoupleSettingDao
import com.project.myapplication.data.dao.RoomDiaryDao
import com.project.myapplication.data.entity.RoomCoupleSettingEntity
import com.project.myapplication.di.KoinApplication.Companion.sharedPreference
import io.reactivex.Single
import java.text.SimpleDateFormat
import java.util.*

class StartRepository:BaseRepository() {
    override val roomDaoImage: RoomDiaryDao
        get() = super.roomDaoImage
    override val roomDaoCoupleSetting: RoomCoupleSettingDao
        get() = super.roomDaoCoupleSetting

    private fun getCoupleDate(): Long {
        val coupleDate = sharedPreference.getCoupleDate()
        val simpleDate = "${coupleDate[0]}-${coupleDate[1]}-${coupleDate[2]} 00:00:00"
        val sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val parseDate = sf.parse(simpleDate)

        val today = Calendar.getInstance()

        return (today.time.time - parseDate.time) / (60 * 60 * 24 * 1000)
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