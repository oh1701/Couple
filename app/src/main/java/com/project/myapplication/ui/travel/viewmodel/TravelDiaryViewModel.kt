package com.project.myapplication.ui.travel.viewmodel

import android.net.Uri
import android.util.Log
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
    private val _diaryImageUri = MutableLiveData<Uri>(null)
    val diaryImageUri : LiveData<Uri> = _diaryImageUri
    private val _imageClick = MutableLiveData<Boolean>()
    val imageClick:LiveData<Boolean> = _imageClick
    private val _createDay = MutableLiveData<String>()
    val createDay:LiveData<String> = _createDay

    fun getImage(){
        compositeDisposable.add(repository.selectDB(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { _diaryImageUri.value = Uri.parse(it.imageUri) }
            .doOnError { Log.e("실패하였음", "실패") }
            .subscribe())
    }

    fun setDiaryImage(){
        _imageClick.value = true
    }

    fun getUri(uri: Uri){
        _diaryImageUri.value = uri

        compositeDisposable.add(repository.insertDB(RoomDiaryEntity(0, uri.toString(), "15", 1414, 14, 14, "!515"))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { Log.e("성공하였음", "성공") }
            .doOnError {  Log.e("실패하였음", "실패")  }
            .subscribe())

        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)

        _createDay.value = sdf.toString()
    }
}