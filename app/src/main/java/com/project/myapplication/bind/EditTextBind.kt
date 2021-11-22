package com.project.myapplication.bind

import android.content.res.ColorStateList
import android.text.Editable
import android.text.InputType
import android.text.Spannable
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.project.myapplication.R
import com.project.myapplication.model.FontSetting

object EditTextBind {
    @JvmStatic
    @BindingAdapter("CursorFontSetting")
    fun EditText.cursorFontSetting(fontSetting: FontSetting?){
        val edit = this
        edit.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(fontSetting != null){
                        val span = edit.text.setSpan(
                            ForegroundColorSpan(fontSetting.colorHex!!.defaultColor),
                            this@cursorFontSetting.selectionStart - 1, //커서의 1개 전.
                            this@cursorFontSetting.selectionStart, // 커서 위치
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        edit.text.removeSpan(span) // Span을 지우지 않으면 다시 설정해도 이전 설정이 적용된다.
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            })
    }

}