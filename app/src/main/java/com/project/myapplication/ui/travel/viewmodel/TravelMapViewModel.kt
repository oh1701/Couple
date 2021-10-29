package com.project.myapplication.ui.travel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.ui.travel.GoogleMapSetting

class TravelMapViewModel():BaseViewModel() {
    private val _myLocationLatLng = MutableLiveData<LatLng>()
    val myLocationLatLng:LiveData<LatLng> = _myLocationLatLng
    private val _onBackPressed = MutableLiveData<Boolean>()
    val onBackPressed:LiveData<Boolean> = _onBackPressed
    private val _geoCoderToLocation = MutableLiveData<String>()
    val geoCoderToLocation:LiveData<String> = _geoCoderToLocation
    private val _createTravelDiary = MutableLiveData<LatLng>()
    val createTravelDiary:LiveData<LatLng> = _createTravelDiary

    fun getMyLatLng(latLng: LatLng){
        _myLocationLatLng.value = latLng
    }


    fun aaa(location:String){
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
}