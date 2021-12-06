package com.project.myapplication.ui.travel.viewpager

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.view.size
import androidx.viewpager2.widget.ViewPager2
import com.project.myapplication.R
import com.project.myapplication.adapter.viewpager_adapter.ViewPagerFullScreenImageAdapter
import com.project.myapplication.adapter.viewpager_adapter.ViewPagerTravelDiaryAdapter
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.databinding.FragmentViewpagerTravelDiaryBinding
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ViewPagerTravelDiaryFragment(private val diaryID:ArrayList<String>):BaseFragment<FragmentViewpagerTravelDiaryBinding, BaseViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_viewpager_travel_diary
    override val thisViewModel: BaseViewModel by viewModel()
    private val sharedViewModel:TravelViewModel by sharedViewModel()
    override fun initView() {
        binding.diaryViewPager.adapter = ViewPagerTravelDiaryAdapter(this, diaryID)
        binding.diaryViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.diaryViewPager.isUserInputEnabled = false // 슬라이드로 못넘어가게 하기.
        binding.diaryViewPager.setCurrentItem(0, true)
        binding.diaryViewPager.offscreenPageLimit = 1 // 좌우 Life 살려두기. 2면 왼쪽 2개, 오른쪽2개.
        binding.indicator.attachToPager(binding.diaryViewPager)
    }

    override fun initObserve() {
    }

    override fun sharedObserve() {
        super.sharedObserve()

        sharedViewModel.check.observe(viewLifecycleOwner, {
            when(it){
                "left" -> {
                    binding.diaryViewPager.setCurrentItem(binding.diaryViewPager.currentItem - 1, true)
                }

                 //null이면 안들어가서 걱정없이 넣어도 된다.
                "right" -> {
                    binding.diaryViewPager.setCurrentItem(binding.diaryViewPager.currentItem + 1, true)
                }
            }
        })
    }
}