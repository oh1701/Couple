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
import android.widget.ImageButton


object ViewPagerBind {
    @JvmStatic
    @BindingAdapter("ViewPagerDiaryImageBind", "ViewPagerLiveImage",
        "ViewPagerindicator", "ViewPagerImagePosition", "ViewPagerLeftBtn", "ViewPagerRightBtn")
    fun viewPagerDiaryImageBind(myViewPager2: ViewPager2, fragment: Fragment,
                  image:ArrayList<String>?, indicator: ScrollingPagerIndicator, imagePosition: (Int) -> Unit, leftBtn: ImageButton,
                                rightBtn: ImageButton
    ){
        myViewPager2.adapter = ViewPagerDiaryImageAdapter(fragment, image, imagePosition, myViewPager2)
        myViewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        myViewPager2.offscreenPageLimit = 1
        indicator.attachToPager(myViewPager2)
        setViewPagerBtnVisibility(myViewPager2, rightBtn, leftBtn, image)

        rightBtn.setOnClickListener {
            myViewPager2.setCurrentItem(myViewPager2.currentItem + 1, true)
            setViewPagerBtnVisibility(myViewPager2, rightBtn, leftBtn, image)
        }

        leftBtn.setOnClickListener {
            myViewPager2.setCurrentItem(myViewPager2.currentItem - 1, true)
            setViewPagerBtnVisibility(myViewPager2, rightBtn, leftBtn, image)
        }
    }

    private fun setViewPagerBtnVisibility(myViewPager2: ViewPager2, rightBtn: ImageButton, leftBtn: ImageButton, image: ArrayList<String>?){
        when {
            myViewPager2.currentItem == 0 -> {
                rightBtn.visibility = View.VISIBLE
                leftBtn.visibility = View.GONE
            }
            myViewPager2.currentItem < (image?.size?: 1) - 1 -> {
                rightBtn.visibility = View.VISIBLE
                leftBtn.visibility = View.VISIBLE
            }
            myViewPager2.currentItem == (image?.size?: 1) - 1 -> {
                rightBtn.visibility = View.GONE
                leftBtn.visibility = View.VISIBLE
            }
            else -> {
                rightBtn.visibility = View.GONE
                leftBtn.visibility = View.GONE }
        }
    }
}