package com.project.myapplication.views.travel

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.project.myapplication.R
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.databinding.ActivityTravelBinding
import org.koin.android.viewmodel.ext.android.viewModel

class TravelActivity:BaseActivity<ActivityTravelBinding, TravelViewModel>(), OnMapReadyCallback {
    override val layoutResourceId: Int
        get() = R.layout.activity_travel
    override val thisViewModel: TravelViewModel by viewModel()
    lateinit var googleMap: GoogleMap
    lateinit var myLocationLatLng: LatLng
    private val checkLocation = LocationListener { location ->
        location.let{
            thisViewModel.getMyLatLng(LatLng(it.latitude, it.longitude))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val map = supportFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment
        map.getMapAsync(this)

        getLocation()
    }

    override fun initView() {
        binding.backPressed.setOnClickListener {
            onBackPressed()
        }

        binding.createTravel.setOnClickListener {
            toast("생성되었음")
        }
    }

    override fun initObserve() {
        thisViewModel.myLocationLatLng.observe(this, { latlng ->
            animateCamera(latlng)
            log("확인", latlng.toString())
        })
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.uiSettings.isTiltGesturesEnabled = false
        googleMap.uiSettings.isRotateGesturesEnabled = false
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        googleMap.isBuildingsEnabled = false
    }

    private fun animateCamera(latlng:LatLng){
        val cameraPosition = CameraPosition.Builder()
            .target(latlng)
            .zoom(17f)
            .build()

        googleMap.animateCamera(
            CameraUpdateFactory.newCameraPosition(cameraPosition)
        )
    }

    private fun getLocation(){
        val lm = getSystemService(LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if(checkSelfPermission()){
            return
        }
        else{
            if(isGpsEnabled && isNetworkEnabled) {
                lm.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 1000, 0.0F, checkLocation
                )
            }
            else{
                toast("GPS 나 데이터 사용이 꺼져있음.")
            }
        }
    }

    private fun checkSelfPermission(): Boolean{ // 퍼미션 확인.
        return (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED)
    }
}