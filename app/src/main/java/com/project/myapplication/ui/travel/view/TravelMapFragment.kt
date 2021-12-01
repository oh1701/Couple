package com.project.myapplication.ui.travel.view

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.maps.android.clustering.ClusterManager
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.databinding.FragmentTravelMapBinding
import com.project.myapplication.googlemap.ClusterSetting
import com.project.myapplication.googlemap.GoogleMapSetting
import com.project.myapplication.googlemap.MarkerClusterItem
import com.project.myapplication.ui.travel.viewmodel.TravelMapViewModel
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import com.project.myapplication.utils.MoveFragment
import com.project.myapplication.utils.customobserver.EventObserver
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/** 나중에 getLocation, getGecoder 분리하기 */
/** LocationListener가 나갔다가 들어오면 오랫동안 작동이 안된다. */

class TravelMapFragment:BaseFragment<FragmentTravelMapBinding, TravelMapViewModel>(), OnMapReadyCallback {
    override val layoutResourceId: Int
        get() = R.layout.fragment_travel_map
    override val thisViewModel: TravelMapViewModel by viewModel()
    private val sharedActivityViewModel: TravelViewModel by sharedViewModel()
    private lateinit var googleMap: GoogleMap
    private lateinit var googleMapSetting: GoogleMapSetting
    private lateinit var cluster : ClusterManager<MarkerClusterItem>
    private val clusterSetting = ClusterSetting()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val map = childFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment
        map.getMapAsync(this)
    }

    override fun initView() {
        binding.travelMapViewModel = thisViewModel
        binding.travelMainViewModel = sharedActivityViewModel
        binding.travelMapFrgament = this
    }

    override fun initObserve() {
        thisViewModel.googleMapAllDiaryMarker.observe(viewLifecycleOwner, EventObserver{ diary ->
            clusterSetting.clusterAddItemList(cluster, diary)
        })

        thisViewModel.googleMapCreateNewMarker.observe(viewLifecycleOwner, EventObserver{ diary ->
            clusterSetting.clusterAddItem(cluster, diary)
        })

        thisViewModel.createTravelDiary.observe(viewLifecycleOwner, EventObserver{ // 다이어리 버튼 눌리면.
            MoveFragment()
                .createDiary(requireActivity().supportFragmentManager, TravelDiaryFragment())
                .addToBackStack("Map")
                .commit()
        })

        thisViewModel.cameraAutoSetting.observe(viewLifecycleOwner){
            if(::googleMapSetting.isInitialized) {
                googleMapSetting.settingCamera(it)
            }
        }
    }

    override fun sharedObserve() {
        super.sharedObserve()

        sharedActivityViewModel.myLocationLatLng.observe(viewLifecycleOwner) { LatLng ->
            if(googleMapSettingCheck()) {
                if(LatLng != null) {
                    googleMapSetting.repeatFunction(LatLng)
                }
                else{
                    toast("위치 정보가 받아와지지 않습니다. 잠시 후 다시 실행해주세요.")
                    requireActivity().onBackPressed()
                }
            }
        }

        sharedActivityViewModel.newCreateMarker.observe(viewLifecycleOwner, EventObserver{ Diaryid ->
            thisViewModel.newCreateMapMarker(Diaryid)
        })
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        if(!googleMapSettingCheck()){ // lateinit 할당되지 않았을 때만 실행
            googleMapSetting = GoogleMapSetting(requireContext(), googleMap)
            googleMapSetting.mapSetting()
            cluster = ClusterManager<MarkerClusterItem>(requireContext(), googleMap)
            clusterSetting.setCluster(requireContext(), cluster, googleMap)
            thisViewModel.getAllDiary()
        }
    }

    private fun googleMapSettingCheck():Boolean{
        return ::googleMapSetting.isInitialized
    }
}