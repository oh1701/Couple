package com.project.myapplication.ui.travel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.project.myapplication.ui.travel.viewpager.ViewPagerDiaryImageFragment

/** 프래그먼트 생성자 문제 때문에 사용하였음. fragment 인자 필요 없는 것들은 companion object 사용했음. */

class ViewPagerDiaryImageFragmentFactory(private val mytag:String?, private val image:String?,private val fragment:Fragment?):FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ViewPagerDiaryImageFragment::class.java.name -> ViewPagerDiaryImageFragment(mytag, image, fragment)
            else -> super.instantiate(classLoader, className)
        }
    }
}