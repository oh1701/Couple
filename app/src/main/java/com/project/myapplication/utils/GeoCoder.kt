package com.project.myapplication.utils

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.util.*

class GeoCoder(private val context: Context) {
     fun getGeoCoder(latlng: LatLng):String{
        val geoCoder = Geocoder(context, Locale.KOREA)
        val location = geoCoder.getFromLocation(latlng.latitude, latlng.longitude, 2)

        return if(location.isNullOrEmpty()){
            "확인되지 않는 지역"
        }
         else {
            val contryName = location[0].countryName
            val adminArea = location[0].adminArea
            val localityName = location[0].locality
            val thoroughfare = location[0].thoroughfare

            val returnLocation = (localityName ?: adminArea) ?: null

            if (returnLocation == null && thoroughfare == null)
                "확인되지 않는 지역"
            else
                "$returnLocation $thoroughfare"
        }
    }
}