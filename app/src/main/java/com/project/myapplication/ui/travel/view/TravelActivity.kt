package com.project.myapplication.ui.travel.view

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.project.myapplication.R
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.databinding.ActivityTravelBinding
import com.project.myapplication.ui.dialog.view.FontDialogFragment
import com.project.myapplication.ui.travel.ViewPagerDiaryImageFragmentFactory
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import com.project.myapplication.utils.GeoCoder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class TravelActivity:BaseActivity<ActivityTravelBinding, TravelViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_travel
    override val thisViewModel: TravelViewModel by viewModel()
    private val getCoder: GeoCoder by inject()
    private lateinit var lm : LocationManager
    var int = 0
    private val checkLocation = LocationListener { location ->
        location.let {
            if (location.provider == LocationManager.GPS_PROVIDER) {
                thisViewModel.getMyLatLng(LatLng(it.latitude, it.longitude))
                thisViewModel.geoCoderToLocation(getCoder.getGeoCoder(LatLng(it.latitude, it.longitude)))
            } else {
                thisViewModel.getMyLatLng(LatLng(it.latitude, it.longitude))
                thisViewModel.geoCoderToLocation(getCoder.getGeoCoder(LatLng(it.latitude, it.longitude)))
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory // super.oncreate 전에 선언해야함
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

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if(!locationCheck(this)){ //
            toast("위치 권한이 설정되어 있지 않습니다. 설정 후 다시 접속해주세요.")
            onBackPressed()
        }
        else{
            if(isGpsEnabled && isNetworkEnabled) {
                lm.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 2000L, 0.0F, checkLocation
                )
                lm.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 2000L, 0.0F, checkLocation
                )
            }
            else{
                toast("GPS 나 데이터 사용이 꺼져있음.")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        lm.removeUpdates(checkLocation)
    }
}