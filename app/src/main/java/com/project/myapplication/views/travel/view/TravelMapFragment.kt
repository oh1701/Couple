package com.project.myapplication.views.travel.view

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.databinding.FragmentTravelMapBinding
import com.project.myapplication.views.travel.GoogleMapSetting
import com.project.myapplication.views.travel.viewmodel.TravelMapViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class TravelMapFragment:BaseFragment<FragmentTravelMapBinding, TravelMapViewModel>(), OnMapReadyCallback {
    override val layoutResourceId: Int
        get() = R.layout.fragment_travel_map
    override val thisViewModel: TravelMapViewModel by viewModel()
    private lateinit var googleMap: GoogleMap
    private lateinit var mapSetting: GoogleMapSetting
    private lateinit var backPressedCallback: OnBackPressedCallback
    private val checkLocation = LocationListener { location ->
        location.let{
            thisViewModel.getMyLatLng(LatLng(it.latitude, it.longitude))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val map = childFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment
        map.getMapAsync(this)
        getLocation()
    }

    override fun initView() {
        binding.backPressed.setOnClickListener {
//            requireActivity().supportFragmentManager.popBackStack()
            requireActivity().onBackPressed()
        }

        binding.createTravel.setOnClickListener {
            toast("생성되었음")
        }
    }

    override fun initObserve() {
        thisViewModel.myLocationLatLng.observe(this, { latlng ->
            mapSetting.repeatFunction(latlng)
        })
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        if(!::mapSetting.isInitialized){ // lateinit 할당되지 않았을 때만 실행
            mapSetting = GoogleMapSetting(requireContext(), googleMap, binding)
            mapSetting.mapSetting()
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
            ).commit()

            return@setOnMarkerClickListener true
        }
    }

    private fun getLocation(){
        val lm = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if(checkSelfPermission()){
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

    private fun checkSelfPermission(): Boolean{ // 퍼미션 확인.
        return (ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED)
    }
}