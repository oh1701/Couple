package com.project.myapplication.bind

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.project.myapplication.ui.Viewpagerrrr
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.relex.circleindicator.CircleIndicator3
import org.koin.ext.getScopeName
import java.util.concurrent.TimeUnit

object ViewPagerBind {
    @JvmStatic
    @BindingAdapter("ViewPagerImageBind", "ViewPagerLiveImage", "ViewPagerindicator")
    fun imageBind(myViewPager2: ViewPager2, fragment: Fragment, image:String?, indicator:CircleIndicator3){
        myViewPager2.adapter = MyAdapter(fragment, image)
        myViewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        myViewPager2.offscreenPageLimit = 1 // 보여지지 않는 프래그먼트 상태 유지 량
        indicator.setViewPager(myViewPager2)
        indicator.createIndicators(6, 0)
    }
}

class MyAdapter(private val fragment: Fragment, private val image: String?) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment { // 현재 포지션에 따라 보여줄 프래그먼트
        return Viewpagerrrr(image)
    }
}