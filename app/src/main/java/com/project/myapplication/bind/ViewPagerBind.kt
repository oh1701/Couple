package com.project.myapplication.bind

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.project.myapplication.adapter.viewpager_adapter.ViewPagerDiaryImageAdapter
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator
import android.view.MotionEvent
import android.view.View


object ViewPagerBind {
    @JvmStatic
    @BindingAdapter("ViewPagerDiaryImageBind", "ViewPagerLiveImage", "ViewPagerindicator", "ViewPagerImagePosition")
    fun viewPagerDiaryImageBind(myViewPager2: ViewPager2, fragment: Fragment,
                  image:ArrayList<String>?, indicator: ScrollingPagerIndicator, imagePosition: (Int) -> Unit){
        myViewPager2.adapter = ViewPagerDiaryImageAdapter(fragment, image, imagePosition, myViewPager2)
        myViewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        myViewPager2.offscreenPageLimit = 1
        indicator.attachToPager(myViewPager2)
    }
}