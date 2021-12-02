package com.project.myapplication.bind

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.project.myapplication.R
import com.project.myapplication.ui.Viewpagerrrr
import com.project.myapplication.ui.travel.view.TravelDiaryFragment
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.relex.circleindicator.CircleIndicator3
import org.koin.ext.getScopeName
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator
import java.util.concurrent.TimeUnit

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
            null, 0 -> Viewpagerrrr(null, fragment)
            else -> Viewpagerrrr(image[position], fragment)
        }
    }
}