package com.project.myapplication.utils

import android.content.Context
import android.graphics.Typeface
import com.project.myapplication.R
import com.project.myapplication.model.font.FontTypeFace
import kotlin.coroutines.coroutineContext

class TextTypeFace {
    fun returnTypefaceArray(context: Context):ArrayList<FontTypeFace>{
        return arrayListOf(
            FontTypeFace(Typeface.DEFAULT, "기본"),
            FontTypeFace(Typeface.DEFAULT_BOLD, "기본 두꺼움"),
            FontTypeFace(context.resources.getFont(R.font.bazzi), "넥슨 배찌"),
            FontTypeFace(context.resources.getFont(R.font.bmeuljiro10yearslater), "을지로 10년 후"),
            FontTypeFace(context.resources.getFont(R.font.cafe24ohsquareair), "카페24 아네모네 에어"),
            FontTypeFace(context.resources.getFont(R.font.cafe24oneprettynight), "카페24 고운밤"),
            FontTypeFace(context.resources.getFont(R.font.cafe24shiningstar), "카페24 빛나는 별"),
            FontTypeFace(context.resources.getFont(R.font.cafe24ssurroundair), "카페24 써라운드 에어"),
            FontTypeFace(context.resources.getFont(R.font.chosuncentennial_ttf), "조선 100년"),
            FontTypeFace(context.resources.getFont(R.font.heiroflightbold), "빛의 사용자 bold"),
            FontTypeFace(context.resources.getFont(R.font.heiroflightregular), "빛의 사용자 regular"),
            FontTypeFace(context.resources.getFont(R.font.koreanfrenchtypewriter), "한불 정부표준 타자기"),
            FontTypeFace(context.resources.getFont(R.font.mapoflower), "마포 꽃"),
            FontTypeFace(context.resources.getFont(R.font.sdsamliphopangchettfbasic), "삼립 호빵"),
            FontTypeFace(context.resources.getFont(R.font.yyour), "너만을 비춤")
        )
    }
}