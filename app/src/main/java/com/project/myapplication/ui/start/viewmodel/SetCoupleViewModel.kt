package com.project.myapplication.ui.start.viewmodel

import android.app.DatePickerDialog
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.data.entity.RoomCoupleSettingEntity
import com.project.myapplication.ui.start.repository.SetCoupleRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SetCoupleViewModel(private val repository: SetCoupleRepository):BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable
    private val _photoUri = MutableLiveData<Uri>()
    val photoUri:LiveData<Uri> = _photoUri

    val userName = MutableLiveData<String>()
    val birthdayYear = MutableLiveData<String>()
    val birthdayMonth = MutableLiveData<String>()
    val birthdayDay = MutableLiveData<String>()

    fun getUri(uri: Uri) {
        _photoUri.value = uri
    }

    fun setBirthDay(year: Int, month: Int, day: Int){
        birthdayYear.value = year.toString()
        birthdayMonth.value = month.toString()
        birthdayDay.value = day.toString()
    }

    fun completeSetting(){
        val completeSetting = RoomCoupleSettingEntity(1, _photoUri.value.toString(), userName.value, birthdayYear.value, birthdayMonth.value, birthdayDay.value, "!4")

        repository.insertCoupleSetting(completeSetting)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { Log.e("성공", "성공") }
            .doOnError{ Log.e("실패", "실패") }
            .subscribe()
    }
}