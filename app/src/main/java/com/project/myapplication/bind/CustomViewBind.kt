package com.project.myapplication.bind

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.github.dhaval2404.colorpicker.ColorPickerView
import org.w3c.dom.Text

object CustomViewBind {
    @JvmStatic
    @BindingAdapter("ColorPickerListener")
    fun ColorPickerView.colorView(textView: TextView){
        this.setColorListener { i, s ->
            textView.setTextColor(Color.parseColor(s))
        }
    }
}