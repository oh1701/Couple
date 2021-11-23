package com.project.myapplication.utils

import android.os.Parcelable
import com.project.myapplication.model.font.FontBindSetting
import kotlinx.parcelize.IgnoredOnParcel

/** Create By Gyu Seong Oh. 2021 / 11  */

@kotlinx.parcelize.Parcelize
class EventCustomCallback(val fontCallback: (FontBindSetting) -> Unit) :Parcelable {
    @IgnoredOnParcel
    lateinit var dataChangeListener: DataChangeListener

    interface DataChangeListener {
            fun fontSettingCallback(index: FontBindSetting)
    }

    fun setChanged(){
        dataChangeListener = object : DataChangeListener{
            override fun fontSettingCallback(index: FontBindSetting) {
                fontCallback.invoke(index)
            }
        }
    }
}