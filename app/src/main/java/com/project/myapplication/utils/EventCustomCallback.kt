package com.project.myapplication.utils

import android.os.Parcelable
import com.project.myapplication.model.font.FontBindSettingModel
import kotlinx.parcelize.IgnoredOnParcel

/** Create By Gyu Seong Oh. 2021 / 11  */

@kotlinx.parcelize.Parcelize
class EventCustomCallback(val fontCallback: (FontBindSettingModel) -> Unit) :Parcelable {
    @IgnoredOnParcel
    lateinit var dataChangeListener: DataChangeListener

    interface DataChangeListener {
            fun fontSettingCallback(index: FontBindSettingModel)
    }

    fun setChanged(){
        dataChangeListener = object : DataChangeListener{
            override fun fontSettingCallback(index: FontBindSettingModel) {
                fontCallback.invoke(index)
            }
        }
    }
}