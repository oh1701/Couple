package com.project.myapplication.ui.travel.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.data.entity.RoomDiaryEntity
import com.project.myapplication.ui.travel.GoogleMapSetting
import com.project.myapplication.ui.travel.repository.TravelMapRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TravelMapViewModel(private val repository: TravelMapRepository):BaseViewModel() {
    private val _googleMapDiaryMarker = MutableLiveData<List<RoomDiaryEntity>>()
    val googleMapDiaryMarker:LiveData<List<RoomDiaryEntity>> = _googleMapDiaryMarker

    fun getAllDiary(){
        compositeDisposable.add(repository.getAllDiary()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _googleMapDiaryMarker.value = it
            }
            .doOnError {
                throwableMessage(it)
            }
            .subscribe())
    }

}