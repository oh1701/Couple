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
import com.project.myapplication.data.room.db.RoomDiaryDB
import com.project.myapplication.data.room.entity.Font
import com.project.myapplication.model.FontSetting
import com.project.myapplication.utils.FontToHtml

object EditTextBind {
    @JvmStatic
    @BindingAdapter("CursorFontSetting", "fontCallback")
    fun EditText.cursorFontSetting(fontSetting: FontSetting?, callback:((String?) -> Unit)?){ //        val font = RoomDiaryDB.INSTANCE!!.font().insertDao(Font())
        val edit = this
        val startHtml = FontToHtml().getStartHtmlFontColor()
        var otherHtml = startHtml + ""

        edit.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, allLength: Int, remove: Int, p3: Int) {
                    if (selectionStart > 0) {
                        if (fontSetting != null && remove == 0) {
                            val span = edit.text.setSpan(
                                ForegroundColorSpan(fontSetting.colorHex!!.defaultColor), //Int
                                this@cursorFontSetting.selectionStart - 1, //커서의 1개 전. Int
                                this@cursorFontSetting.selectionStart, // 커서 위치 Int
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )

                            otherHtml += FontToHtml().getHtmlColorSetting(fontSetting.colorHex.defaultColor, p0.toString().substring(edit.selectionStart - 1, edit.selectionStart))

                             callback.let { // 콜백
                                callback!!.invoke(otherHtml)
                            } // 색깔 지정 안한것도 설정해줘야함.

                            edit.text.removeSpan(span) // Span을 지우지 않으면 다시 설정해도 이전 설정이 적용된다.
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }
        })
    }
}