package com.project.myapplication.ui.travel.view

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.common.CheckSelfPermission
import com.project.myapplication.databinding.FragmentTravelMapBinding
import com.project.myapplication.ui.travel.GoogleMapSetting
import com.project.myapplication.ui.travel.viewmodel.TravelMapViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class TravelMapFragment:BaseFragment<FragmentTravelMapBinding, TravelMapViewModel>(), OnMapReadyCallback {
    override val layoutResourceId: Int
        get() = R.layout.fragment_travel_map
    override val thisViewModel: TravelMapViewModel by viewModel()
    private lateinit var googleMap: GoogleMap
    private lateinit var googleMapSetting: GoogleMapSetting
    private val checkSelfPermission:CheckSelfPermission by inject()
    private val checkLocation = LocationListener { location ->
        location.let{
            thisViewModel.getMyLatLng(LatLng(it.latitude, it.longitude))
            thisViewModel.aaa(getGeoCoder(LatLng(it.latitude, it.longitude)))
        }
    }

    override fun initView() {
        binding.travelMap = thisViewModel

        val map = childFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment
        map.getMapAsync(this)
        getLocation()
    }

    override fun initObserve() {
        thisViewModel.myLocationLatLng.observe(this, { LatLng ->
            googleMapSetting.repeatFunction(LatLng)
        })

        thisViewModel.createTravelDiary.observe(this, { LatLng ->
            googleMapSetting.addDiaryMarker(LatLng)
        })

        thisViewModel.onBackPressed.observe(this, {
            requireActivity().onBackPressed()
        })
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        if(!::googleMapSetting.isInitialized){ // lateinit 할당되지 않았을 때만 실행
            googleMapSetting = GoogleMapSetting(requireContext(), googleMap)
            googleMapSetting.mapSetting()
        }

        googleMap.setOnMarkerClickListener {
            supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in_bottom,
                0,
                0,
                R.anim.slide_out_bottom
            ).add(
                R.id.fragment_layout,
                TravelDiaryFragment()
            ).addToBackStack("Map").commit()

            return@setOnMarkerClickListener true
        }
    }

    private fun getLocation(){
        val lm = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if(checkSelfPermission(requireContext())){
            return
        }
        else{
            if(isGpsEnabled && isNetworkEnabled) {
                lm.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 3000, 0.0F, checkLocation
                )
            }
            else{
                toast("GPS 나 데이터 사용이 꺼져있음.")
            }
        }
    }

    private fun getGeoCoder(latlng: LatLng):String{
        val geoCoder = Geocoder(context, Locale.KOREA)
        val location = geoCoder.getFromLocation(latlng.latitude, latlng.longitude, 2)

        val contryName = location[0].countryName
        val localityName = location[0].locality

        return "$contryName\n $localityName"
    }
}