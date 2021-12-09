package com.project.myapplication.ui.travel.view

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.collections.MarkerManager
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.databinding.FragmentTravelMapBinding
import com.project.myapplication.googlemap.ClusterSetting
import com.project.myapplication.googlemap.GoogleMapSetting
import com.project.myapplication.googlemap.MarkerClusterItem
import com.project.myapplication.ui.travel.viewmodel.TravelMapViewModel
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import com.project.myapplication.ui.travel.viewpager.ViewPagerTravelDiaryFragment
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
    private var lastClickTime = 0L

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
        thisViewModel.googleMapAllDiaryMarker.observe(viewLifecycleOwner, EventObserver{ AddAlldiary ->
            clusterSetting.clusterItemList(cluster, AddAlldiary)
        })

        thisViewModel.googleMapCreateNewMarker.observe(viewLifecycleOwner, EventObserver{ createDiary ->
            clusterSetting.clusterItem(cluster, createDiary, "ADD")
        })

        thisViewModel.googleMapRemoveMarker.observe(viewLifecycleOwner, EventObserver{ removeDiary ->
            clusterSetting.clusterItem(cluster, removeDiary, "REMOVE")
        })

        thisViewModel.toastLiveData.observe(viewLifecycleOwner, {
            toast(it.peekContent())
        })

        thisViewModel.createTravelDiary.observe(viewLifecycleOwner, EventObserver{ // 다이어리 버튼 눌리면.
            if(sharedActivityViewModel.myLocationLatLng.value == null){
                toast("위치 정보가 불러와지지 않았습니다.\n잠시 후 다시 시도해주세요.")
            } else {
                if ((SystemClock.elapsedRealtime() - lastClickTime) > 1000) { // 중복 클릭 방지
                    MoveFragment()
                        .createDiary(
                            requireActivity().supportFragmentManager,
                            TravelDiaryFragment.newInstance(9999999, -1)
                        )
                        .addToBackStack("Map")
                        .commit()
                }
                lastClickTime = SystemClock.elapsedRealtime()
            }
        })

        thisViewModel.cameraAutoSetting.observe(viewLifecycleOwner){
            if(::googleMapSetting.isInitialized) {
                googleMapSetting.settingCamera(it)
            }
        }

        thisViewModel.markerClickListener.observe(viewLifecycleOwner, EventObserver{ MarkerID ->
            MoveFragment()
                .createDiary(requireActivity().supportFragmentManager, ViewPagerTravelDiaryFragment.newInstance(MarkerID))
                .addToBackStack("Map")
                .commit()
        })
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

        sharedActivityViewModel.newCreateMarker.observe(viewLifecycleOwner, EventObserver{ AddDiaryid ->
            thisViewModel.newCreateMapMarker(AddDiaryid)
        })


        sharedActivityViewModel.removeMarker.observe(viewLifecycleOwner, EventObserver{ removeDiaryID ->
            thisViewModel.removeMarkerGetEntity(removeDiaryID)
        })
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        if(!googleMapSettingCheck()){ // lateinit 할당되지 않았을 때만 실행
            googleMapSetting = GoogleMapSetting(requireContext(), googleMap)
            googleMapSetting.mapSetting()
            cluster = ClusterManager<MarkerClusterItem>(requireContext(), googleMap)
            clusterSetting.setCluster(requireContext(), cluster, googleMap, thisViewModel)
            thisViewModel.getAllDiary()
        }
    }

    private fun googleMapSettingCheck():Boolean{
        return ::googleMapSetting.isInitialized
    }
}