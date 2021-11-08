package com.project.myapplication.data.sharedpreference

import android.content.Context
import android.content.SharedPreferences

class IntroSettingShared(context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    fun setIntroCoupleDateSetting(check : Boolean){
        editor.putBoolean("IntroCoupleDate", check)
        editor.apply()
    }

    fun getIntroCoupleDateSetting():Boolean{
        return prefs.getBoolean("IntroCoupleDate", false)
    }
}