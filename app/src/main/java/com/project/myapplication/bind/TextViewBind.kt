package com.project.myapplication.bind

import android.widget.TextView
import androidx.databinding.BindingAdapter

object TextViewBind {
    @JvmStatic
    @BindingAdapter("coupleNameText")
    fun TextView.coupleNameText(text:String?){
        if(text != null && text.isNotEmpty()){
            this.text = text
        }
        else {
            this.text = "이름"
        }
    }
}