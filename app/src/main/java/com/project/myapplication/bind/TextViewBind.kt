package com.project.myapplication.bind

import android.widget.TextView
import androidx.databinding.BindingAdapter

object TextViewBind {
    @JvmStatic
    @BindingAdapter("coupleNameText")
    fun TextView.coupleNameText(text:String?){
        if(text != null){
            this.text = text
        }
    }
}