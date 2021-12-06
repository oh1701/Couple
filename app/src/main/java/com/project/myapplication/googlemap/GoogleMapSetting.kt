package com.project.myapplication.googlemap

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.project.myapplication.R
import com.project.myapplication.utils.GlideUtils


/** 맵 패키지 안에 마커 , 카메라 부분 등등으로 나누기*/
class GoogleMapSetting(val context: Context, private val googleMap: GoogleMap?) {
    private lateinit var userMarker:Marker
    private lateinit var userMarkerImage:BitmapDescriptor
    private var cameraMoving = true
    private val view = LayoutInflater.from(context).inflate(R.layout.google_usermarker, null)
    private val imageView = view.findViewById<ImageView>(R.id.profile_image)
    private val glideUtils = GlideUtils(context)

    // Setting

    fun mapSetting(){
        googleMap?.uiSettings?.isTiltGesturesEnabled = false // 카메리 각도 조절하는 설정 끄기
        googleMap?.uiSettings?.isRotateGesturesEnabled = false // 제스쳐로 회전 시키는 설정 끄기
        googleMap?.uiSettings?.isMyLocationButtonEnabled = false // 내 위치 버튼 나오게하는 설정 끄기
        googleMap?.uiSettings?.isMapToolbarEnabled = false // Toolbar 나오게 하는 설정 끄기
    }

    // Camera

    fun repeatFunction(latlng: LatLng){
        animateCamera(latlng)
        userCreateMarker(latlng)
    }

    fun settingCamera(set:Boolean){
        cameraMoving = set
    }

    private fun animateCamera(latlng: LatLng){
        if(cameraMoving) {
            val cameraPosition = CameraPosition.Builder()
                .target(latlng)
                .zoom(17f)
                .build()

            googleMap?.animateCamera(
                CameraUpdateFactory.newCameraPosition(cameraPosition)
            )
        }
    }

    // Marker

    private fun userCreateMarker(latlng: LatLng){
        if(::userMarker.isInitialized && userMarker.zIndex == 1.5f){
//            마커가 존재할 시, addMarker말고 움직임으로 표현하기.
        }
        else{
            userMarker = googleMap?.addMarker(MarkerOptions()
                .zIndex(1.5f)
                .position(latlng))!!
            glideUtils.glideListener(view, R.drawable.couple3, imageView, userMarker, "user")
        }
    }
}