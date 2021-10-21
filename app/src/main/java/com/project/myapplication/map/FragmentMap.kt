package com.project.myapplication.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.databinding.FragmentMapBinding
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentMap:BaseFragment<FragmentMapBinding, BaseViewModel>(), OnMapReadyCallback {
    override val layoutResourceId: Int
        get() = R.layout.fragment_map
    override val thisViewModel: BaseViewModel by viewModel()
    lateinit var googleMap: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun initView() {
        val map = childFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment
        map.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
    }

    override fun initObserve() {

    }
}