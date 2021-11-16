package com.project.myapplication.utils

/** 정규식 확인 */

class RegaxCheck {
    fun koreanRegax(string:String):Boolean{
        val reg = Regex("^[가-힣]\$")
        return string.matches(reg)
    }

    fun numberRegax(string:String):Boolean{
        val reg = Regex("^[0-9]\$")
        return string.matches(reg)
    }
}