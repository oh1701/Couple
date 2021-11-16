package com.project.myapplication.ui.travel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.utils.Event
import com.project.myapplication.data.room.entity.RoomDiaryEntity
import com.project.myapplication.ui.travel.repository.TravelMapRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TravelMapViewModel(private val repository: TravelMapRepository):BaseViewModel() {
    private val _googleMapAllDiaryMarker = MutableLiveData<Event<List<RoomDiaryEntity>>>()
    val googleMapAllDiaryMarker:LiveData<Event<List<RoomDiaryEntity>>> = _googleMapAllDiaryMarker
    private val _googleMapCreateNewMarker = MutableLiveData<Event<RoomDiaryEntity>>()
    val googleMapCreateNewMarker:MutableLiveData<Event<RoomDiaryEntity>> = _googleMapCreateNewMarker
    private val _createTravelDiary = MutableLiveData<Event<Boolean>>()
    val createTravelDiary:LiveData<Event<Boolean>> = _createTravelDiary
    private val _cameraAutoSetting = MutableLiveData<Boolean>()
    val cameraAutoSetting:LiveData<Boolean> = _cameraAutoSetting

    init {
        _cameraAutoSetting.value = true
    }

    fun getAllDiary(){ // 저장된 다이어리들 가져오기.
        compositeDisposable.add(repository.getAllDiary()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _googleMapAllDiaryMarker.value = Event(it)
            }
            .doOnError {
                throwableMessage(it)
            }
            .subscribe())
    }

    fun newCreateMapMarker(id:Int){
        compositeDisposable.add(repository.getIdDiary(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _googleMapCreateNewMarker.value = Event(it)
            }
            .doOnError {
                throwableMessage(it)
            }
            .subscribe()
        )
    }

    fun createDiaryButton(){
        _createTravelDiary.value = Event(true)
    }

    fun cameraAutoSet(){
        _cameraAutoSetting.value = _cameraAutoSetting.value?.not()
    }
}