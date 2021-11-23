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
import com.project.myapplication.bind.EditTextBind.cursorFontSetting
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

    }
}
