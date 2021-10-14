package com.project.myapplication

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import com.project.myapplication.base.BaseRepository

class MainRepository:BaseRepository() {
    fun getDateTime():SpannableStringBuilder{
        var span = SpannableStringBuilder("우리 사랑\n벌써 ${1}일")
        span.setSpan(RelativeSizeSpan(0.6f), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return span
    }
}