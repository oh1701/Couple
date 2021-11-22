package com.project.myapplication.bind

import android.graphics.Color
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.github.dhaval2404.colorpicker.ColorPickerView

object CustomViewBind {
    @JvmStatic
    @BindingAdapter("ColorPickerListener", "buttonColor")
    fun ColorPickerView.colorView(editText: EditText, buttonColor: Int?){
        if(buttonColor != null){
            editText.setTextColor(buttonColor)
            editText.setHintTextColor(buttonColor)
        }

        this.setColorListener { _, colorhex ->
            editText.setTextColor(Color.parseColor(colorhex))
            editText.setHintTextColor(Color.parseColor(colorhex))
        }
    }
}