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

    fun fontToString(font: Typeface, context: Context): String {
        return when (font) {
            Typeface.DEFAULT -> "기본"
            Typeface.DEFAULT_BOLD -> "기본 두꺼움"
            context.resources.getFont(R.font.bazzi) -> "배찌"
            context.resources.getFont(R.font.bmeuljiro10yearslater) -> "을지로 10년 후"
            context.resources.getFont(R.font.cafe24ohsquareair) -> "카페24 아네모네 에어"
            context.resources.getFont(R.font.cafe24oneprettynight) -> "카페24 고운밤"
            context.resources.getFont(R.font.cafe24shiningstar) -> "카페24 빛나는 별"
            context.resources.getFont(R.font.cafe24ssurroundair) -> "카페24 써라운드 에어"
            context.resources.getFont(R.font.chosuncentennial_ttf) -> "조선 100년"
            context.resources.getFont(R.font.heiroflightbold) -> "빛의 사용자 bold"
            context.resources.getFont(R.font.heiroflightregular) -> "빛의 사용자 regular"
            context.resources.getFont(R.font.koreanfrenchtypewriter) -> "한불 정부표준 타자기"
            context.resources.getFont(R.font.mapoflower) -> "마포 꽃"
            context.resources.getFont(R.font.sdsamliphopangchettfbasic) -> "삼립 호빵"
            context.resources.getFont(R.font.yyour) -> "너만을 비춤"
            else -> "기본"
        }
    }

    fun stringToFont(font: String, context: Context): Typeface {
        return when (font) {
            "기본" -> Typeface.DEFAULT
            "기본 두꺼움" -> Typeface.DEFAULT_BOLD
            "배찌" -> context.resources.getFont(R.font.bazzi)
            "을지로 10년 후" -> context.resources.getFont(R.font.bmeuljiro10yearslater)
            "카페24 아네모네 에어" -> context.resources.getFont(R.font.cafe24ohsquareair)
            "카페24 고운밤" -> context.resources.getFont(R.font.cafe24oneprettynight)
            "카페24 빛나는 별" -> context.resources.getFont(R.font.cafe24shiningstar)
            "카페24 써라운드 에어" -> context.resources.getFont(R.font.cafe24ssurroundair)
            "조선 100년" -> context.resources.getFont(R.font.chosuncentennial_ttf)
            "빛의 사용자 bold" -> context.resources.getFont(R.font.heiroflightbold)
            "빛의 사용자 regular" -> context.resources.getFont(R.font.heiroflightregular)
            "한불 정부표준 타자기" -> context.resources.getFont(R.font.koreanfrenchtypewriter)
            "마포 꽃" -> context.resources.getFont(R.font.mapoflower)
            "삼립 호빵" -> context.resources.getFont(R.font.sdsamliphopangchettfbasic)
            "너만을 비춤" -> context.resources.getFont(R.font.yyour)
            else -> Typeface.DEFAULT
        }
    }
}