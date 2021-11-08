package com.project.myapplication.bind

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.TextViewBindingAdapter
import com.project.myapplication.common.RegaxCheck
import com.project.myapplication.ui.start.viewmodel.SetCoupleViewModel
import java.util.*

object EditTextBind {
    @JvmStatic
    @BindingAdapter("content")
    fun EditText.inputEditTextContent(content: String?){
        val old = this.text.toString()
        if(old != content){
            this.setText(content)
        }
    }

    @JvmStatic
    @BindingAdapter("contentAttrChanged")
    fun EditText.inputEditTextContentTextWatcher(listener: InverseBindingListener?){
        val watcher = object : TextWatcher{
            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(text: Editable?) {
                listener?.onChange()
            }
        }

        this.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "content", event = "contentAttrChanged")
    fun EditText.getContent():String{
        return this.text.toString()
    }
}