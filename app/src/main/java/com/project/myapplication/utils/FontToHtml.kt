package com.project.myapplication.utils

/** Html.toHtml 하면 Span 중복되어서 섞인 색깔들이 안나와서 직접 만듦. */
/** Create By Gyu Seong Oh. 2021 / 11 / 22 */
/** SpannableStringBuilder(SpannableString(Html.fromhtml(문자값))) 으로 Text 넣으면 된다.*/

/** 여러가지 문제로 인해 폐기. 나중에 연구해보기. */

class FontToHtml {
    private val startHtml = "<p dir=\"ltr\">"
    private var otherHtml = startHtml + ""
    val endHtml = "</p>"

    fun getStartHtmlFontColor():String{
        return startHtml
    }

    fun getHtmlColorSetting(color:Int, getStr:String): String{
        val colorString = String.format("#%06X", (0xFFFFFF and color))
        return "<span style=\"color:$colorString;\">$getStr</span>" // otherHtml에 += 로 넣어줘야함.
    }

    fun colorIntToString(color:Int):String{
        return String.format("#%06X", (0xFFFFFF and color))
    }
}