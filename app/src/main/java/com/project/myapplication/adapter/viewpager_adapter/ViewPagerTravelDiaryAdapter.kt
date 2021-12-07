package com.project.myapplication.adapter.viewpager_adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.maps.android.clustering.Cluster
import com.project.myapplication.googlemap.MarkerClusterItem
import com.project.myapplication.ui.travel.view.TravelDiaryFragment

class ViewPagerTravelDiaryAdapter(private val fragment: Fragment, private val markerList: ArrayList<String>?): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return markerList?.size ?: 0
    }

    override fun createFragment(position: Int): Fragment {
        return TravelDiaryFragment.newInstance(markerList?.get(position)?.toIntOrNull() ?: 9999999)
    }
}