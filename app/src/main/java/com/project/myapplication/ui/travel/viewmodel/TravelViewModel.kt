package com.project.myapplication.ui.travel.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.utils.customobserver.Event
import io.reactivex.disposables.CompositeDisposable

class TravelViewModel:BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable
    private val _myLocationLatLng = MutableLiveData<LatLng>()
    val myLocationLatLng: LiveData<LatLng> = _myLocationLatLng
    private val _geoCoderToLocation = MutableLiveData<String>()
    val geoCoderToLocation:LiveData<String> = _geoCoderToLocation
    private val _newCreateMarker = MutableLiveData<Event<Int>>()
    val newCreateMarker:LiveData<Event<Int>> = _newCreateMarker
    private val _removeMarker = MutableLiveData<Event<Int>>()
    val removeMarker:LiveData<Event<Int>> = _removeMarker
    private val _viewPagerBtnCheck = MutableLiveData<Event<Boolean>>()
    val viewPagerBtnCheck:LiveData<Event<Boolean>> = _viewPagerBtnCheck

    init{
        _geoCoderToLocation.value = "현재 위치를 찾고 있습니다 ..."
    }
    
    fun getMyLatLng(latLng: LatLng){
        _myLocationLatLng.value = latLng
    }

    fun geoCoderToLocation(location:String){
        _geoCoderToLocation.value = location
    }

    fun newCreateMarker(id:Int){ // 다이어리 저장 버튼 누를시 맵 마커 create
        _newCreateMarker.value = Event(id)
    }

    fun removeMarker(id:Int){
        _removeMarker.value = Event(id)
    }

    fun getViewPagerBtnString(boolean:Boolean){
        _viewPagerBtnCheck.value = Event(boolean)
    }
}