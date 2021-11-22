package com.project.myapplication.utils

import android.app.Dialog
import android.content.Context
import android.util.DisplayMetrics

/** Create By Gyu Seong Oh. 2021 / 11  */

class DialogSettings(private val context: Context) {
    private var metrics: DisplayMetrics = context.resources.displayMetrics

    fun dialogMetricsSetting(dialog: Dialog?, width:Int, height:Int){
        val lp = dialog?.window?.attributes
        lp?.width = metrics.widthPixels * width / 10 //레이아웃 params 에 width, height 넣어주기.
        lp?.height = metrics.heightPixels * height / 10
        dialog?.window?.attributes = lp // 다이얼로그 표출 넓이 넣어주기.
    }
}