package com.project.myapplication.ui.travel.viewmodel

import android.net.Uri
import android.opengl.Visibility
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.data.entity.RoomDiaryEntity
import com.project.myapplication.ui.travel.repository.TravelDiaryRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

/** insertDB는 작성완료했을때 RoomDiaryEntity를 파라미터값으로 받는다.*/

class TravelDiaryViewModel(private val repository: TravelDiaryRepository):BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable
    private val _diaryImageUri = MutableLiveData<String>(null)
    val diaryImageUri : LiveData<String> = _diaryImageUri
    private val _createDay = MutableLiveData<String>()
    val createDay:LiveData<String> = _createDay
    private val _createDiaryLocation = MutableLiveData<String>()
    val createDiaryLocation:LiveData<String> = _createDiaryLocation
    private val _createDiaryCoupleDay = MutableLiveData<String>()
    val createDiaryCoupleDay:LiveData<String> = _createDiaryCoupleDay
    private val _diaryViewVisibility = MutableLiveData<Int>()
    val diaryViewVisibility:LiveData<Int> = _diaryViewVisibility
    private val _diaryTrashBtnCheck = MutableLiveData<Boolean>()
    val diaryTrashBtnCheck:LiveData<Boolean> = _diaryTrashBtnCheck
    private val _diaryTouchBtnCheck = MutableLiveData<Boolean>()
    val diaryTouchBtnCheck:LiveData<Boolean> = _diaryTouchBtnCheck
    private val _diaryTagBtnCheck = MutableLiveData<Boolean>()
    val diaryTagBtnCheck:LiveData<Boolean> = _diaryTagBtnCheck

    init{
        _diaryTrashBtnCheck.value = false
        _diaryTouchBtnCheck.value = false
        _diaryTagBtnCheck.value = false
    }

    fun getImage(){
        compositeDisposable.add(repository.selectDB(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { _diaryImageUri.value = it.imageUri }
            .doOnError { Log.e("실패하였음", "실패") }
            .subscribe())
    }

    fun getUri(uri: Uri){
        _diaryImageUri.value = uri.toString()

        compositeDisposable.add(repository.insertDB(RoomDiaryEntity(0, uri.toString(), "15", 1414, 14, 14))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { Log.e("성공하였음", "성공") }
            .doOnError {  Log.e("실패하였음", "실패")  }
            .subscribe())
    }

    fun createDiary(){
        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)

        _createDay.value = sdf.toString()
        _createDiaryCoupleDay.value = repository.getDateday()
    }

    fun changedButtonCheck(view: View){
        when(view.tag){
            "touch" -> { _diaryTouchBtnCheck.value = _diaryTouchBtnCheck.value?.not() }
            "tag" -> { _diaryTagBtnCheck.value = _diaryTagBtnCheck.value?.not() }
            "trash" -> { _diaryTrashBtnCheck.value = _diaryTrashBtnCheck.value?.not() }
        }
    }
}