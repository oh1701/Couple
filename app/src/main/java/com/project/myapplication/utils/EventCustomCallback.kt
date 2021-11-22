package com.project.myapplication.utils

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import com.project.myapplication.model.FontSetting
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel

@kotlinx.parcelize.Parcelize
class EventCustomCallback(val fontCallback: (FontSetting) -> Unit) :Parcelable {
    @IgnoredOnParcel
    lateinit var dataChangeListener: DataChangeListener

    interface DataChangeListener {
            fun fontSettingCallback(index: FontSetting)
    }

    fun setChanged(){
        dataChangeListener = object : DataChangeListener{
            override fun fontSettingCallback(index: FontSetting) {
                fontCallback.invoke(index)
            }
        }
    }
}