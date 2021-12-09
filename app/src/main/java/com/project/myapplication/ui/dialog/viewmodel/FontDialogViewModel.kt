package com.project.myapplication.ui.dialog.viewmodel

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.model.font.FontBindSettingModel
import com.project.myapplication.utils.customobserver.Event
import com.project.myapplication.utils.customobserver.ArrayListMutableLiveData

//ArrayList[0] ~ [4] ColorStateList 만들어야함
class FontDialogViewModel:BaseViewModel() {
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
    private val _textTypeFace = MutableLiveData<Typeface>(Typeface.DEFAULT)
    val textTypeFace:LiveData<Typeface> = _textTypeFace
    private val _buttonColor = MutableLiveData<Int>()
    val buttonColor:LiveData<Int> = _buttonColor

    val backgroundTint = ArrayListMutableLiveData<ColorStateList>()
    val fontColorCallBack:(ColorStateList) -> Unit = {
        _textColor.value = it.defaultColor
    }

    init{
        backgroundTint.listLiveData()
        backgroundTint.add(ColorStateList.valueOf(-570425344))
    }

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
                        _textSize.value = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _textTypedValue.value!!, metrics.value)
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
                        _textSize.value = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _textTypedValue.value!!, metrics.value)
                    }
                    else -> toast("사이즈가 최소치입니다.")
                }
            }
        }
    }

    fun dialogComplete(){
        if (backgroundTint.value?.size!! < 6) { // 최근 색깔 지정
            backgroundTint.add(ColorStateList.valueOf(textColor.value!!))
        } else {
            for (i in 5 downTo 1) {
                if(i > 1)
                    backgroundTint.value!![i] = backgroundTint.value!![i - 1]
                else if(i == 1){
                    backgroundTint.value!![i] = (ColorStateList.valueOf(textColor.value!!))
                }
            }
        }
        _fontSettingComplete.value = Event(true)
    }

    //6개중에서 0번째는 검정 고정. 1번째는 현재, 2번째는 -1 3번째는 -1 4번째는 -1 5번째는 -1

    fun setEditTextColor(v:View){
        _buttonColor.value = v.backgroundTintList?.defaultColor
    }

    fun getFontSetting(fontSettingModel:FontBindSettingModel?){
        _textSize.value = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _textTypedValue.value!!, metrics.value)
        if(fontSettingModel != null){
            _textTypedValue.value = fontSettingModel.fontTypedSizeValue
            _lineSpacing.value = fontSettingModel.lineSpacing
            _letterSpacing.value = fontSettingModel.letterSpacing
            _textColor.value = fontSettingModel.colorHex!!.defaultColor
            _textTypeFace.value = fontSettingModel.fontTypeFace
            backgroundTint.allChange(fontSettingModel.colorList)
        }
    }
}