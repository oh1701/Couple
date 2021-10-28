package com.project.myapplication.ui.travel

import android.content.Context
import android.location.Geocoder
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.project.myapplication.databinding.FragmentTravelMapBinding
import java.util.*

class GoogleMapSetting(val context: Context, private val googleMap: GoogleMap) {
    private lateinit var marker:Marker
    private var cameraMoving = true

    fun mapSetting(){
        googleMap.uiSettings.isTiltGesturesEnabled = false
        googleMap.uiSettings.isRotateGesturesEnabled = false
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        googleMap.isBuildingsEnabled = false
    }

    fun repeatFunction(latlng: LatLng){
        animateCamera(latlng)
        userCreateMarker(latlng)
    }

    private fun animateCamera(latlng: LatLng){
        if(cameraMoving) {
            val cameraPosition = CameraPosition.Builder()
                .target(latlng)
                .zoom(17f)
                .build()

            googleMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(cameraPosition)
            )
        }
    }

    private fun userCreateMarker(latlng: LatLng){
        if(::marker.isInitialized){
//            마커가 존재할 시, addMarker말고 움직임으로 표현하기.
            Log.e("마커 존재함", "마커 존재함")
        }
        else{
            Log.e("마커 존재하지 않음", "마커 존재하지 않음")
            addDiaryMarker(latlng) // 커스텀마커로 icon 설정
        }
    }

    fun addDiaryMarker(latlng: LatLng){
        marker = googleMap.addMarker(MarkerOptions()
            .position(latlng)
            .zIndex(5.0f))!!
        Log.e("마커 생성", "생성하였음.")
    }

    private fun onOffCameraAnimate(){ // 마커 따라가는 카메라 움직임을 onOff하는 함수.
        cameraMoving = !cameraMoving
    }
}