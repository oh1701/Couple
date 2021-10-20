package com.project.myapplication.travel

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import com.project.myapplication.base.BaseRepository

class FragmentRepository:BaseRepository() {
    fun getDateTime(): SpannableStringBuilder { // 나중에 서버 or DB 연동해서 Day지정.
        var span = SpannableStringBuilder("우리 사랑\n벌써 ${1}일") // 나중에 이건 DB에서 가져올거임.
        span.setSpan(RelativeSizeSpan(0.6f), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return span
    }
}