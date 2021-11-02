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
    private val _myLocationLatLng = MutableLiveData<LatLng>()
    val myLocationLatLng:LiveData<LatLng> = _myLocationLatLng
    private val _onBackPressed = MutableLiveData<Boolean>()
    val onBackPressed:LiveData<Boolean> = _onBackPressed
    private val _geoCoderToLocation = MutableLiveData<String>()
    val geoCoderToLocation:LiveData<String> = _geoCoderToLocation
    private val _createTravelDiary = MutableLiveData<LatLng>()
    val createTravelDiary:LiveData<LatLng> = _createTravelDiary
    private val _googleMapDiaryMarker = MutableLiveData<List<RoomDiaryEntity>>()
    val googleMapDiaryMarker:LiveData<List<RoomDiaryEntity>> = _googleMapDiaryMarker

    fun getMyLatLng(latLng: LatLng){
        _myLocationLatLng.value = latLng
    }

    fun geoCoderToLocation(location:String){
        _geoCoderToLocation.value = location
    }

    fun onBackPressedButton(){
        _onBackPressed.value = true
    }

    fun createDiaryButton(){
        if(_myLocationLatLng.value != null){
            _createTravelDiary.value = _myLocationLatLng.value
        }
    }

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