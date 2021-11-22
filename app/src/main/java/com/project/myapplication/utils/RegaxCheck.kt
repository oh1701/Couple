package com.project.myapplication.utils

/** 정규식 확인 */

/** Create By Gyu Seong Oh. 2021 / 10  */

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