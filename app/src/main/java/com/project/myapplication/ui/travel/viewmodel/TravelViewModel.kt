package com.project.myapplication.ui.travel.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.common.Event
import io.reactivex.disposables.CompositeDisposable

class TravelViewModel:BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable
    private val _myLocationLatLng = MutableLiveData<LatLng>()
    val myLocationLatLng: LiveData<LatLng> = _myLocationLatLng
    private val _createTravelDiary = MutableLiveData<Event<Boolean>>()
    val createTravelDiary:LiveData<Event<Boolean>> = _createTravelDiary
    private val _geoCoderToLocation = MutableLiveData<String>()
    val geoCoderToLocation:LiveData<String> = _geoCoderToLocation

    init{
        _geoCoderToLocation.value = "현재 위치를 찾고 있습니다 ..."
    }
    
    fun getMyLatLng(latLng: LatLng){
        _myLocationLatLng.value = latLng
    }

    fun geoCoderToLocation(location:String){
        _geoCoderToLocation.value = location
    }

    fun createDiaryButton(){
        _createTravelDiary.value = Event(true)
    }

}