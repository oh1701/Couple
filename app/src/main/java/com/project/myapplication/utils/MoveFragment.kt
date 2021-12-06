package com.project.myapplication.utils

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.project.myapplication.R

/** Create By Gyu Seong Oh. 2021 / 10  */

class MoveFragment {
    fun createDiary(fragmentManager: FragmentManager, fragmentName:Fragment): FragmentTransaction {
        return fragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_in_bottom,
            0,
            0,
            R.anim.slide_out_bottom
        ).add(
            R.id.fragment_layout,
            fragmentName
        )
    }

    fun addFragmentUp(fragmentManager: FragmentManager, fragmentName:Fragment, moveLayout:Int, tag:String?): FragmentTransaction {
        return fragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_in_bottom,
            0,
            0,
            R.anim.slide_out_bottom
        ).add(
            moveLayout,
            fragmentName,
            tag
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