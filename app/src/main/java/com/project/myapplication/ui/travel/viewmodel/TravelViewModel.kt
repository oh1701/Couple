package com.project.myapplication.ui.travel.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.project.myapplication.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class TravelViewModel:BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable
    private val _myLocationLatLng = MutableLiveData<LatLng>()
    val myLocationLatLng: LiveData<LatLng> = _myLocationLatLng
    private val _createTravelDiary = MutableLiveData<Boolean>()
    val createTravelDiary:LiveData<Boolean> = _createTravelDiary
    private val _geoCoderToLocation = MutableLiveData<String>()
    val geoCoderToLocation:LiveData<String> = _geoCoderToLocation

    fun getMyLatLng(latLng: LatLng){
        _myLocationLatLng.value = latLng
    }

    fun geoCoderToLocation(location:String){
        _geoCoderToLocation.value = location
    }

    fun createDiaryButton(){
        _createTravelDiary.value = true
    }

}