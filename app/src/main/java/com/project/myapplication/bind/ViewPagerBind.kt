package com.project.myapplication.bind

import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.project.myapplication.ui.travel.viewpager.ViewPagerImageAdapter
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator

object ViewPagerBind {
    @JvmStatic
    @BindingAdapter("ViewPagerImageBind", "ViewPagerLiveImage", "ViewPagerindicator", "ViewPagerImagePosition")
    fun imageBind(myViewPager2: ViewPager2, fragment: Fragment,
                  image:ArrayList<String>?, indicator: ScrollingPagerIndicator, imagePosition: (Int) -> Unit){
        myViewPager2.adapter = MyAdapter(fragment, image, imagePosition, myViewPager2)
        myViewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        myViewPager2.offscreenPageLimit = 1
        indicator.attachToPager(myViewPager2)
    }
}

class MyAdapter(private val fragment: Fragment, private val image: ArrayList<String>?,
                private val imagePosition: (Int) -> Unit, private val myViewPager2: ViewPager2) : FragmentStateAdapter(fragment){
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
            null, 0 -> ViewPagerImageAdapter(null, fragment)
            else -> ViewPagerImageAdapter(image[position], fragment)
        }
    }
}