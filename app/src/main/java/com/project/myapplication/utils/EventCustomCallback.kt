package com.project.myapplication.utils

import android.os.Parcelable
import com.project.myapplication.model.font.FontBindSettingModel
import kotlinx.parcelize.IgnoredOnParcel

/** Create By Gyu Seong Oh. 2021 / 11  */

@kotlinx.parcelize.Parcelize
class EventCustomCallback<T>(val fontCallback: (T) -> Unit) :Parcelable {
    @IgnoredOnParcel
    lateinit var dataChangeListener: DataChangeListener<T>

    interface DataChangeListener <T>{
        fun myCustomCallback(index: T)
    }

    fun setChanged(){
        dataChangeListener = object : DataChangeListener<T>{
            override fun myCustomCallback(index: T) {

            }
        }
    }
}