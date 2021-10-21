package com.project.myapplication.common

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.google.android.gms.maps.model.LatLng

object MyLocation {
    var myGpsLocation:LatLng? = null
    val checkLocation = object : LocationListener{
        override fun onLocationChanged(location: Location) {
            location.let{
                myGpsLocation = LatLng(it.latitude, it.longitude)
            }
        }

        override fun onLocationChanged(locations: MutableList<Location>) {
            super.onLocationChanged(locations)
        }

        override fun onFlushComplete(requestCode: Int) {
            super.onFlushComplete(requestCode)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            super.onStatusChanged(provider, status, extras)
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
        }
    }
}