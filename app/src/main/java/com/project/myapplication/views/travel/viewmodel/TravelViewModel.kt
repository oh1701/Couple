package com.project.myapplication.views.travel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.project.myapplication.base.BaseViewModel

class TravelViewModel():BaseViewModel() {
    private val _myLocationLatLng = MutableLiveData<LatLng>()
    val myLocationLatLng:LiveData<LatLng> = _myLocationLatLng

    fun getMyLatLng(latLng: LatLng){
        _myLocationLatLng.value = latLng
    }
}