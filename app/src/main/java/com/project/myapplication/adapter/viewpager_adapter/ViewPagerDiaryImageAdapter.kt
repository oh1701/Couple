package com.project.myapplication.adapter.viewpager_adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.project.myapplication.ui.travel.ViewPagerDiaryImageFragmentFactory
import com.project.myapplication.ui.travel.viewpager.ViewPagerDiaryImageFragment

class ViewPagerDiaryImageAdapter(private val fragment: Fragment, private val image: ArrayList<String>?,
                                 private val imagePosition: (Int) -> Unit, private val myViewPager2: ViewPager2
) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return when (image?.size) {
            null, 0 -> 1
            else -> image.size
        }
    }

    override fun containsItem(itemId: Long): Boolean {
        return super.containsItem(itemId)
    }

    override fun getItemId(position: Int): Long {
        imagePosition.invoke(myViewPager2.currentItem) // ViewPager 현재 포지션 Callback
        return super.getItemId(position)
    }

    override fun createFragment(position: Int): Fragment { // 현재 포지션에 따라 보여줄 프래그먼트
        return when (image?.size) {
            null, 0 -> ViewPagerDiaryImageFragmentFactory("diary", null, fragment).instantiate(
                ClassLoader.getSystemClassLoader(), ViewPagerDiaryImageFragment::class.java.name)
            else -> ViewPagerDiaryImageFragmentFactory("diary", image[position], fragment).instantiate(
                ClassLoader.getSystemClassLoader(), ViewPagerDiaryImageFragment::class.java.name)
        }
    }
}