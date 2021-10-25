package com.project.myapplication.common

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.project.myapplication.R

class MapSetting(private val googleMap: GoogleMap) {
    private lateinit var marker:Marker
    fun mapSetting(){
        googleMap.uiSettings.isTiltGesturesEnabled = false
        googleMap.uiSettings.isRotateGesturesEnabled = false
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        googleMap.isBuildingsEnabled = false
    }

    fun repeatFunction(latlng: LatLng){
        animateCamera(latlng)
        createMarker(latlng)
    }

    private fun animateCamera(latlng: LatLng){
        val cameraPosition = CameraPosition.Builder()
            .target(latlng)
            .zoom(17f)
            .build()

        googleMap.animateCamera(
            CameraUpdateFactory.newCameraPosition(cameraPosition)
        )
    }

    private fun createMarker(latlng: LatLng){
        if(::marker.isInitialized){
//            googleMap.
        }
        else{
            googleMap.addMarker(MarkerOptions()
                .position(latlng)
                .zIndex(5.0f))
        }
    }
}