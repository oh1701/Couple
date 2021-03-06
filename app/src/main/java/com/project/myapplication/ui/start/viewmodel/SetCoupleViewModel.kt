package com.project.myapplication.ui.start.viewmodel

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.utils.customobserver.Event
import com.project.myapplication.data.room.entity.RoomCoupleSettingEntity
import com.project.myapplication.ui.start.repository.SetCoupleRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SetCoupleViewModel(private val repository: SetCoupleRepository):BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable
    private val _photoUri = MutableLiveData<String>()
    val photoUri: LiveData<String> = _photoUri
    private val _completeButtonAlpha = MutableLiveData<Float>()
    val completeButtonAlpha: LiveData<Float> = _completeButtonAlpha
    private val _complete = MutableLiveData<Boolean>()
    val complete: LiveData<Boolean> = _complete
    private val _settingExist = MutableLiveData<Boolean>() // 이전에 설정한 정보가 존재하는지 확인
    private val settingExist: LiveData<Boolean> = _settingExist
    private val _setImageClick = MutableLiveData<Event<Boolean>>()
    val setImageClick: LiveData<Event<Boolean>> = _setImageClick
    private val _setBirthClick = MutableLiveData<Event<Boolean>>()
    val setBirthClick: LiveData<Event<Boolean>> = _setBirthClick

    init {
        _settingExist.value = false
    }

    val userName = MutableLiveData<String>()
    val birthdayYear = MutableLiveData<String>()
    val birthdayMonth = MutableLiveData<String>()
    val birthdayDay = MutableLiveData<String>()

    fun getUri(ImageData: Intent?) {
        _photoUri.value = ImageData?.data.toString()
    }

    fun setBirthDay(year: Int, month: Int, day: Int) {
        birthdayYear.value = year.toString()
        birthdayMonth.value = month.toString()
        birthdayDay.value = day.toString()
    }

    fun completeSetting(id: Int) { // 버튼 완료 버튼 클릭 시 실행
        val completeSetting = RoomCoupleSettingEntity(
            id,
            _photoUri.value.toString(),
            userName.value,
            birthdayYear.value,
            birthdayMonth.value,
            birthdayDay.value
        )
        Log.e("completeSetting::", "complete")
        if (settingExist.value == true) {
            updateCoupleSetting(id, completeSetting)
        } else {
            insertCoupleSetting(id, completeSetting)
        }
    }

    fun getCoupleSetting(id: Int) { // 프래그먼트 열리면 데이터들을 라이브데이터 (xml 뷰) 에 전달.
        repository.getCoupleSetting(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { room ->
                birthdayYear.value = room.birthYear
                birthdayMonth.value = room.birthMonth
                birthdayDay.value = room.birthDay
                userName.value = room.name
                _photoUri.value = room.uri
                _settingExist.value = true
                Log.e("Room ::", "getCoupleSetting success")
            }
            .doOnError {
                _settingExist.value = false
                Log.e("Room ::", "getCoupleSetting failed")
            }
            .subscribe()
    }

    private fun updateCoupleSetting(id: Int, update: RoomCoupleSettingEntity) {
        repository.updateCoupleSetting(update)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                Log.e("Room ::", "CoupleSetting Update Success")
                _complete.value = true
            }
            .doOnError { Log.e("Room ::", "CoupleSetting Update Failed") }
            .subscribe()
    }

    private fun insertCoupleSetting(id: Int, insert: RoomCoupleSettingEntity) {
        repository.insertCoupleSetting(insert)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                Log.e("Room ::", "CoupleSetting insert Success")
                _complete.value = true
            }
            .doOnError { Log.e("Room ::", "CoupleSetting insert Failed") }
            .subscribe()
    }

    fun onClick(v: View) {
        when (v.tag) {
            "setImage" -> _setImageClick.value = Event(true)
            "birth" -> _setBirthClick.value = Event(true)
        }
    }
}