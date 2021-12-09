package com.project.myapplication.ui.travel.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.project.myapplication.R
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.utils.customobserver.Event
import com.project.myapplication.data.room.entity.RoomDiaryEntity
import com.project.myapplication.googlemap.MarkerClusterItem
import com.project.myapplication.ui.travel.repository.TravelMapRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TravelMapViewModel(private val repository: TravelMapRepository):BaseViewModel() {
    private val _googleMapAllDiaryMarker = MutableLiveData<Event<List<RoomDiaryEntity>>>()
    val googleMapAllDiaryMarker:LiveData<Event<List<RoomDiaryEntity>>> = _googleMapAllDiaryMarker
    private val _googleMapCreateNewMarker = MutableLiveData<Event<RoomDiaryEntity>>()
    val googleMapCreateNewMarker:LiveData<Event<RoomDiaryEntity>> = _googleMapCreateNewMarker
    private val _googleMapRemoveMarker = MutableLiveData<Event<RoomDiaryEntity>>()
    val googleMapRemoveMarker:LiveData<Event<RoomDiaryEntity>> = _googleMapRemoveMarker
    private val _createTravelDiary = MutableLiveData<Event<Boolean>>()
    val createTravelDiary:LiveData<Event<Boolean>> = _createTravelDiary
    private val _cameraAutoSetting = MutableLiveData<Boolean>()
    val cameraAutoSetting:LiveData<Boolean> = _cameraAutoSetting
    private val _markerClickListener = MutableLiveData<Event<ArrayList<String>>>()
    val markerClickListener:LiveData<Event<ArrayList<String>>> = _markerClickListener

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

    fun removeMarkerGetEntity(id:Int){
        compositeDisposable.add(repository.getIdDiary(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _googleMapRemoveMarker.value = Event(it)
                removeMarker(id)
            }
            .doOnError {
                throwableMessage(it)
            }
            .subscribe()
        )
    }

    private fun removeMarker(id:Int){
        compositeDisposable.add(
            repository
                .removeDiaryDB(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    toast("다이어리 제거 성공")
                }
                .doOnError {
                    Log.e("Error, RemoveDiary ::", "$it")
                    toast("다이어리 제거 실패")
                }
                .subscribe()
        )
    }

    fun createDiaryButton(){
        _createTravelDiary.value = Event(true)
    }

    fun cameraAutoSet(){
        _cameraAutoSetting.value = _cameraAutoSetting.value?.not()

        if(cameraAutoSetting.value!!)
            toast("카메라 자동 조정")
        else
            toast("카메라 수동 조정")
    }

    fun markerClickListener(id:ArrayList<String>){
        _markerClickListener.value = Event(id)
    }
}
