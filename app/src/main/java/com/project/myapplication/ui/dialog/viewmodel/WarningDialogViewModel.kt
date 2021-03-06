package com.project.myapplication.ui.dialog.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.application.KoinApplication
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.utils.customobserver.Event
import com.project.myapplication.ui.dialog.repository.WarningDialogRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

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
    private val _markerRemoveShared = MutableLiveData<Event<Int>>()
    val markerRemoveShared : LiveData<Event<Int>> = _markerRemoveShared

    fun settingDialog(tag: String?) {
        text(tag)
        when(tag){// removeDiary ID는 태그로 지정해줬음.
            "Permission" -> {
                _positiveButtonTag.value = "설정"
                _negativeButtonTag.value = "취소"
            }
            "closeDiary" -> {
                _positiveButtonTag.value = "나가기"
                _negativeButtonTag.value = "취소"
            }
            "removeImage" -> {
                _positiveButtonTag.value = "예"
                _negativeButtonTag.value = "아니오"
            }
            else -> {
                _positiveButtonTag.value = "삭제"
                _negativeButtonTag.value = "취소"
            }
        }
    }

    fun selectButton(v: View){
        when(v.tag){// removeDiary ID는 태그로 지정해줬음.
            "나가기" -> { _selectButtonClick.value = Event("onBackPressed") }
            "설정" -> { _selectButtonClick.value = Event("Setting") }
            "예" -> { _selectButtonClick.value = Event("removeImage") }
            "삭제" -> {_selectButtonClick.value = Event("removeDiary")}
            "취소", "아니오" -> { _selectButtonClick.value = Event("dismiss") }
        }
    }

    fun text(tag : String?){
        when(tag){
            "Permission" -> _warningText.value = "정상적인 기능 사용을 위해 설정에서 카메라 및 저장정보 권한을 활성화 시켜주세요."
            "closeDiary" -> _warningText.value = "작성중인 내용이 존재합니다.\n해당 내용을 저장하지 않고 나가시겠습니까?"
            "removeImage" -> _warningText.value = "이 이미지를 삭제하시겠습니까?"
            else -> _warningText.value = "이 게시물을 삭제하시겠습니까?\n삭제된 게시물은 복구되지 않습니다."
        }
    }

    fun removeDiary(id:Int){
        _markerRemoveShared.value = Event(id)
    }
}