package com.project.myapplication.ui.travel.view

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
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
import com.project.myapplication.common.MoveFragment
import com.project.myapplication.databinding.FragmentTravelMapBinding
import com.project.myapplication.ui.diary.DiaryFragment
import com.project.myapplication.ui.travel.GoogleMapSetting
import com.project.myapplication.ui.travel.viewmodel.TravelMapViewModel
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import io.reactivex.Observable
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

/** 나중에 getLocation, getGecoder 분리하기 */
/** LocationListener가 나갔다가 들어오면 오랫동안 작동이 안된다. */

class TravelMapFragment:BaseFragment<FragmentTravelMapBinding, TravelMapViewModel>(), OnMapReadyCallback {
    override val layoutResourceId: Int
        get() = R.layout.fragment_travel_map
    override val thisViewModel: TravelMapViewModel by viewModel()
    private val sharedActivityViewModel: TravelViewModel by sharedViewModel()
    private lateinit var googleMap: GoogleMap
    private lateinit var googleMapSetting: GoogleMapSetting

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val map = childFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment
        map.getMapAsync(this)
    }

    override fun initView() {
        binding.travelMapViewModel = thisViewModel
        binding.travelMainViewModel = sharedActivityViewModel
        binding.travelMapFrgament = this

        thisViewModel.getAllDiary()
    }

    override fun initObserve() {
        sharedActivityViewModel.myLocationLatLng.observe(this) { LatLng ->
            if(LatLng != null) {
                googleMapSetting.repeatFunction(LatLng)
            }
            else{
                toast("위치 정보가 받아와지지 않습니다. 잠시 후 다시 실행해주세요.")
                requireActivity().onBackPressed()
            }
        }

        sharedActivityViewModel.createTravelDiary.observe(this){
            MoveFragment()
                .createDiary(requireActivity().supportFragmentManager, TravelDiaryFragment())
                .addToBackStack("Map")
                .commit()
        }

        thisViewModel.googleMapDiaryMarker.observe(this, {
            it.map { diary ->
                googleMapSetting.addDiaryMarker(LatLng(diary.latitude.toDouble(), diary.longitude.toDouble())) }
        })
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        if(!::googleMapSetting.isInitialized){ // lateinit 할당되지 않았을 때만 실행
            googleMapSetting = GoogleMapSetting(requireContext(), googleMap)
            googleMapSetting.mapSetting()
        }

        googleMap.setOnMarkerClickListener {
            if(it.title != "user") {
                moveFragment
                    .addFragmentUp(supportFragmentManager, TravelDiaryFragment(), R.id.fragment_layout)
                    .addToBackStack("Map")
                    .commit()
            }

            return@setOnMarkerClickListener true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        googleMap.clear()
    }
}