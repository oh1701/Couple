package com.project.myapplication.bind

import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.project.myapplication.adapter.DiaryImageViewPagerAdapter
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator

object ViewPagerBind {
    @JvmStatic
    @BindingAdapter("ViewPagerImageBind", "ViewPagerLiveImage", "ViewPagerindicator", "ViewPagerImagePosition")
    fun imageBind(myViewPager2: ViewPager2, fragment: Fragment,
                  image:ArrayList<String>?, indicator: ScrollingPagerIndicator, imagePosition: (Int) -> Unit){
        myViewPager2.adapter = DiaryImageViewPagerAdapter(fragment, image, imagePosition, myViewPager2)
        myViewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        myViewPager2.offscreenPageLimit = 1
        indicator.attachToPager(myViewPager2)
    }
}