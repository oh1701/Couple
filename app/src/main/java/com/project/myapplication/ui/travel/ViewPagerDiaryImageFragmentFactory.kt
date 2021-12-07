package com.project.myapplication.ui.travel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.project.myapplication.ui.travel.viewpager.ViewPagerDiaryImageFragment
import com.project.myapplication.ui.travel.viewpager.ViewPagerTravelDiaryFragment

/** 프래그먼트 생성자 문제 때문에 사용하였음. fragment 인자 필요 없는 것들은 companion object 사용했음. */

class ViewPagerDiaryImageFragmentFactory():FragmentFactory() {
    // ViewPagerDiaryImageFragment
    private var mytag:String? = null
    private var image:String? = null
    private var fragment:Fragment? = null

    // ViewPagerTravelDiaryFragment
    private var diaryID:ArrayList<String>? = null

    fun getViewPagerDiaryImageFragment(mytag:String?, image:String?, fragment:Fragment?){
        this.mytag = mytag
        this.image = image
        this.fragment = fragment
    }

    fun getViewPagerTravelDiaryFragment(diaryID:ArrayList<String>?, fragment: Fragment?){
        this.diaryID = diaryID
        this.fragment = fragment
    }

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ViewPagerDiaryImageFragment::class.java.name -> ViewPagerDiaryImageFragment(mytag, image, fragment)
//            ViewPagerTravelDiaryFragment::class.java.name -> ViewPagerTravelDiaryFragment(diaryID, fragment)
            else -> super.instantiate(classLoader, className)
        }
    }
}