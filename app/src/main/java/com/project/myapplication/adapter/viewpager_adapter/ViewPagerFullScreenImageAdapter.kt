package com.project.myapplication.adapter.viewpager_adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.myapplication.ui.travel.viewpager.ViewPagerDiaryImageFragment

class ViewPagerFullScreenImageAdapter(private val fragment: Fragment, private val image: ArrayList<String>?) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return when (image?.size) {
            null, 0 -> 1
            else -> image.size
        }
    }

    override fun createFragment(position: Int): Fragment { // 현재 포지션에 따라 보여줄 프래그먼트
        return when (image?.size) {
            null, 0 -> ViewPagerDiaryImageFragment(null, fragment, "fullScreen")
            else -> ViewPagerDiaryImageFragment(image[position], fragment, "fullScreen")
        }
    }
}