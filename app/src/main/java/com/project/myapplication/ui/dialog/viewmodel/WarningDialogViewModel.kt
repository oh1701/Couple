package com.project.myapplication.ui.dialog.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.application.KoinApplication
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.utils.customobserver.Event
import com.project.myapplication.ui.dialog.repository.WarningDialogRepository
import io.reactivex.disposables.CompositeDisposable

class WarningDialogViewModel(private val repository: WarningDialogRepository):BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable
    override val toastLiveData: LiveData<Event<String>>
        get() = super.toastLiveData
    val koinApplication:KoinApplication = KoinApplication()

    //tag Setting
    private val _positiveButtonTag = MutableLiveData<String>()
    val positiveButtonTag:LiveData<String> = _positiveButtonTag
    private val _negativeButtonTag = MutableLiveData<String>()
    val negativeButtonTag:LiveData<String> = _negativeButtonTag

    private val _selectButtonClick = MutableLiveData<Event<String>>()
    val selectButtonClick:LiveData<Event<String>> = _selectButtonClick
    private val _warningText = MutableLiveData<String>()
    val warningText:LiveData<String> = _warningText

    fun settingDialog(tag: String?) {
        text(tag)
        if (tag == "Permission") {
            _positiveButtonTag.value = "설정"
            _negativeButtonTag.value = "취소"
        } else {
            _positiveButtonTag.value = "삭제"
            _negativeButtonTag.value = "취소"
        }
    }

    fun selectButton(v: View){
        when(v.tag){
            "삭제" -> { _selectButtonClick.value = Event("onBackPressed") }
            "취소" -> { _selectButtonClick.value = Event("dismiss") }
            "설정" -> { _selectButtonClick.value = Event("Setting") }
        }
    }

    fun text(tag : String?){
        if (tag == "Permission")
            _warningText.value = "정상적인 기능 사용을 위해 설정에서 카메라 및 저장정보 권한을 활성화 시켜주세요."
        else
            _warningText.value = "작성중인 내용이 존재합니다.\n해당 내용을 저장하지 않고 삭제하시겠습니까?"
    }
}