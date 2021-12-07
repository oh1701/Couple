package com.project.myapplication.adapter.viewpager_adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.myapplication.ui.travel.ViewPagerDiaryImageFragmentFactory
import com.project.myapplication.ui.travel.viewpager.ViewPagerDiaryImageFragment
import org.koin.core.KoinComponent
import org.koin.core.inject

class ViewPagerFullScreenImageAdapter(private val fragment: Fragment, private val image: ArrayList<String>?)
    : FragmentStateAdapter(fragment),KoinComponent {
    private val viewPagerDiaryImageFragmentFactory: ViewPagerDiaryImageFragmentFactory by inject()

    override fun getItemCount(): Int {
        return when (image?.size) {
            null, 0 -> 1
            else -> image.size
        }
    }

    override fun createFragment(position: Int): Fragment { // 현재 포지션에 따라 보여줄 프래그먼트
        return when (image?.size) {
            null, 0 -> {
                viewPagerDiaryImageFragmentFactory.getViewPagerDiaryImageFragment(
                    "fullScreen",
                    null,
                    fragment
                )
                viewPagerDiaryImageFragmentFactory.instantiate(
                    ClassLoader.getSystemClassLoader(), ViewPagerDiaryImageFragment::class.java.name
                )
            }
            else -> {
                viewPagerDiaryImageFragmentFactory.getViewPagerDiaryImageFragment(
                    "fullScreen",
                    image[position],
                    fragment
                )
                viewPagerDiaryImageFragmentFactory.instantiate(
                    ClassLoader.getSystemClassLoader(), ViewPagerDiaryImageFragment::class.java.name
                )
            }
        }
    }
}