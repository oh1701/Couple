package com.project.myapplication.bind

import android.text.Editable
import android.text.Spannable
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.project.myapplication.model.font.FontBindSettingModel
import com.project.myapplication.utils.FontToHtml

object EditTextBind {
    var fontSettingModel:FontBindSettingModel? = null // font 기입될 때마다 바꿔주는 용
    private var firstTextWatcher = true // TextWatcher 중복 방지용 (처음 설정에서만 TextWatcher 설정)

    /** DiaryContent, DiaryTitle과 같이 폰트 사용하는 것들 callback 받기 위한 용도. */
    @JvmStatic
    @BindingAdapter("CursorFontSetting", "fontCallback")
    fun cursorFontSetting(editText: EditText, fontSettingsModel: FontBindSettingModel?, callback:((String?) -> Unit)?){ //        val font = RoomDiaryDB.INSTANCE!!.font().insertDao(Font())
        val startHtml = FontToHtml().getStartHtmlFontColor()
        var otherHtml = startHtml + ""
        fontSettingModel = fontSettingsModel

        editText.apply{
            letterSpacing = fontSettingModel!!.letterSpacing!!
            setLineSpacing(0.0f, fontSettingModel!!.lineSpacing!!)
            textSize = fontSettingModel!!.fontTypedSizeValue!!
        }

        if(firstTextWatcher) {
            val watcher = object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(
                    p0: CharSequence?,
                    allLength: Int,
                    remove: Int,
                    p3: Int
                ) {
                    if (editText.selectionStart > 0) { // 커서 위치가 1 이상이되었을때
                        if (fontSettingModel != null && remove == 0) { // Setting이 nul이 아니거나 제거하는 상황이 아니면
                            val span = editText.text.setSpan(
                                ForegroundColorSpan(fontSettingModel!!.colorHex!!.defaultColor), //Int
                                editText.selectionStart - 1, //커서의 1개 전. Int
                                editText.selectionStart, // 커서 위치 Int
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )

                            otherHtml += FontToHtml().getHtmlColorSetting(
                                fontSettingModel!!.colorHex!!.defaultColor,
                                p0.toString()
                                    .substring(editText.selectionStart - 1, editText.selectionStart)
                            )
                            callback?.invoke(otherHtml)
                            editText.text.removeSpan(span) // Span을 지우지 않으면 다시 설정해도 이전 설정이 적용된다.
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            }

            editText.addTextChangedListener(watcher)
            firstTextWatcher = false
        }
    }
}
