package com.project.myapplication.common

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.project.myapplication.R
import com.project.myapplication.ui.travel.view.TravelDiaryFragment

class MoveFragment {
    fun checkNowFragment(fragmentManager: FragmentManager): Fragment? {
        return if(fragmentManager.fragments.size > 0){
            fragmentManager.fragments[0]
        }
        else{
            null
        }
    }

    fun moveFragment(fragmentManager: FragmentManager, fragmentName:Fragment){
        if(checkNowFragment(fragmentManager) != null){
            if(checkNowFragment(fragmentManager)!!::class.simpleName.toString() != fragmentName::class.simpleName){
                fragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                    .replace(
                        R.id.inside_fragment,
                        fragmentName
                    ).commit()
            }

            Log.e("현재 프래그먼트는", checkNowFragment(fragmentManager)!!::class.simpleName.toString())
            Log.e("바뀔 프래그먼트는", fragmentName::class.simpleName.toString())
        }
    }

    fun createDiary(fragmentManager: FragmentManager, fragmentName:Fragment): FragmentTransaction {
        return fragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_in_bottom,
            0,
            0,
            R.anim.slide_out_bottom
        ).add(
            R.id.fragment_layout,
            fragmentName,
            "create"
        )
    }

    fun addFragmentUp(fragmentManager: FragmentManager, fragmentName:Fragment, moveLayout:Int): FragmentTransaction {
        return fragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_in_bottom,
            0,
            0,
            R.anim.slide_out_bottom
        ).add(
            moveLayout,
            fragmentName
        )
    }

    fun addFragmentIn(fragmentManager: FragmentManager, fragmentName:Fragment, moveLayout:Int): FragmentTransaction {
        return fragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_in_right,
            R.anim.slide_out_left,
            R.anim.slide_in_left,
            R.anim.slide_out_right
        ).add(
            moveLayout,
            fragmentName
        )
    }

    fun addNoAnimationFragment(fragmentManager: FragmentManager, fragmentName:Fragment, moveLayout:Int): FragmentTransaction {
        return fragmentManager
            .beginTransaction()
            .add(
            moveLayout,
            fragmentName
        )
    }
}