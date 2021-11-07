package com.project.myapplication.common

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

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