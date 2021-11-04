package com.project.myapplication.ui.travel.view

import android.content.Context
import android.location.Geocoder
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.google.android.gms.maps.model.LatLng
import com.project.myapplication.R
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.common.CheckSelfPermission
import com.project.myapplication.databinding.ActivityTravelBinding
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class TravelActivity:BaseActivity<ActivityTravelBinding, TravelViewModel>(), CheckSelfPermission {
    override val layoutResourceId: Int
        get() = R.layout.activity_travel
    override val thisViewModel: TravelViewModel by viewModel()
    private var geoCoder: Geocoder? = null
    private val checkLocation = LocationListener { location ->
        location.let{
            thisViewModel.getMyLatLng(LatLng(it.latitude, it.longitude))
            thisViewModel.geoCoderToLocation(getGeoCoder(LatLng(it.latitude, it.longitude)))
            toast("실행")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getLocation()
    }

    override fun initView() {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_layout,
            TravelMapFragment()
        ).commit()
    }

    override fun initObserve() {

    }

    private fun getLocation(){
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if(checkMyPermission(this)){
            return
        }
        else{
            if(isGpsEnabled && isNetworkEnabled) {
                lm.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000L, 0.0F, checkLocation
                )
            }
            else{
                toast("GPS 나 데이터 사용이 꺼져있음.")
            }
        }
    }

    private fun getGeoCoder(latlng: LatLng):String{
        geoCoder = Geocoder(this, Locale.KOREA)
        val location = geoCoder!!.getFromLocation(latlng.latitude, latlng.longitude, 2)

        val contryName = location[0].countryName
        val adminArea = location[0].adminArea
        val localityName = location[0].locality
        val thoroughfare = location[0].thoroughfare

        return "$adminArea $localityName $thoroughfare"
    }
}