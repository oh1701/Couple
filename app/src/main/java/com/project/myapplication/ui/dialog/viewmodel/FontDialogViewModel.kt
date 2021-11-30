package com.project.myapplication.ui.dialog.viewmodel

import android.graphics.Color
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.model.font.FontBindSettingModel
import com.project.myapplication.utils.customobserver.Event

class FontDialogViewModel:BaseViewModel() {
    private val _metrics = MutableLiveData<DisplayMetrics>()
    private val _letterSpacing = MutableLiveData(0.0f)
    val letterSpacing:LiveData<Float> = _letterSpacing
    private val _lineSpacing = MutableLiveData(1.0f)
    val lineSpacing:LiveData<Float> = _lineSpacing
    private val _textTypedValue = MutableLiveData(16f)
    val textTypedValue:LiveData<Float> = _textTypedValue
    private val _textSize = MutableLiveData<Float>()
    val textSize:LiveData<Float> = _textSize
    private val _fontSettingComplete = MutableLiveData<Event<Boolean>>()
    val fonSettingComplete:LiveData<Event<Boolean>> = _fontSettingComplete
    private val _textColor = MutableLiveData<Int>(Color.parseColor("#000000"))
    val textColor:LiveData<Int> = _textColor
    private val _buttonColor = MutableLiveData<Int>()
    val buttonColor:LiveData<Int> = _buttonColor

    fun plusSizeFont(v: View){
        when(v.tag){
            "lineSpacing" -> {
                when(_lineSpacing.value!!) {
                    in 1.0f .. 1.79f -> _lineSpacing.value = _lineSpacing.value!! + 0.2f
                    else -> toast("줄 간격이 최대치입니다.")
                }
            }
            "letterSpacing" -> {
                when(_letterSpacing.value!!){
                    in 0.0f .. 0.49f -> _letterSpacing.value = _letterSpacing.value!! + 0.1f
                    else -> toast("자간이 최대치입니다.")
                }
            }
            "textSize" -> {
                when(_textTypedValue.value!!){
                    in 16.0f .. 19.9f -> {
                        _textTypedValue.value = _textTypedValue.value!! + 1f
                        _textSize.value = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _textTypedValue.value!!, _metrics.value)
                    }
                    else -> toast("사이즈가 최대치입니다.")
                }
            }
        }
    }

    fun minusSizeFont(v: View){
        when(v.tag){
            "lineSpacing" -> {
                when(_lineSpacing.value!!) {
                    in 1.19f .. 1.9f -> _lineSpacing.value = _lineSpacing.value!! - 0.2f
                    else -> toast("줄 간격이 최소치입니다.")
                }
            }
            "letterSpacing" -> {
                when(_letterSpacing.value!!){
                    in 0.09f .. 0.55f -> _letterSpacing.value = _letterSpacing.value!! - 0.1f
                    else -> toast("자간이 최소치입니다.")
                }
            }
            "textSize" -> {
                when(_textTypedValue.value!!){
                    in 16.1f .. 20.9f -> {
                        _textTypedValue.value = _textTypedValue.value!! - 1f
                        _textSize.value = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _textTypedValue.value!!, _metrics.value)
                    }
                    else -> toast("사이즈가 최소치입니다.")
                }
            }
        }
    }

    fun getMetrics(metrics: DisplayMetrics){
        _metrics.value = metrics
        _textSize.value = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _textTypedValue.value!!, _metrics.value)
    }

    fun dialogComplete(){
        _fontSettingComplete.value = Event(true)
    }

    fun setEditTextColor(v:View){
        _buttonColor.value = v.backgroundTintList?.defaultColor
    }

    fun getFontSetting(fontSettingModel:FontBindSettingModel?){
        if(fontSettingModel != null){
            _textTypedValue.value = fontSettingModel.fontTypedSizeValue
            _lineSpacing.value = fontSettingModel.lineSpacing
            _letterSpacing.value = fontSettingModel.letterSpacing
            _textColor.value = fontSettingModel.colorHex!!.defaultColor
        }
    }
}