package com.project.myapplication.ui.dialog.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.R
import com.project.myapplication.application.ApplicationViewModel
import com.project.myapplication.application.KoinApplication
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.common.Event
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
        if (tag == "Permission") {
            _positiveButtonTag.value = "설정"
            _negativeButtonTag.value = "취소"
        } else {
            _positiveButtonTag.value = "저장"
            _negativeButtonTag.value = "삭제"
        }
    }

    fun selectButton(v: View){
        when(v.tag){
            "삭제" -> { _selectButtonClick.value = Event("onBackPressed") }
            "취소" -> { _selectButtonClick.value = Event("dismiss") }
            "설정" -> { _selectButtonClick.value = Event("Setting") }
            "저장" -> { _selectButtonClick.value = Event("save") }
        }
    }

    fun saveContent(){ //Diary 나 등등 여러개 저장.

    }
}