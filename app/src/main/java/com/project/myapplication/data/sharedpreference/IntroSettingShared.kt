package com.project.myapplication.data.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class IntroSettingShared(context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
    private val editor = prefs.edit()
    private val makeGson = GsonBuilder().create()
    private val listType:TypeToken<List<Int?>> = object : TypeToken<List<Int?>>(){}

    // Set
    fun setIntroCoupleDateSetting(check : Boolean){
        editor.putBoolean("IntroCoupleDate", check)
        editor.apply()
    }

    fun setCoupleDate(date:List<Int?>){
        val strContractDate = makeGson.toJson(date, listType.type)
        editor.putString("CoupleDate", strContractDate)
        editor.commit()
    }

    // Get
    fun getIntroCoupleDateSetting():Boolean{
        return prefs.getBoolean("IntroCoupleDate", false)
    }

    fun getCoupleDate():List<Int?>{
        val getListString = prefs.getString("CoupleDate", "")
        return makeGson.fromJson(getListString, listType.type)
    }
}