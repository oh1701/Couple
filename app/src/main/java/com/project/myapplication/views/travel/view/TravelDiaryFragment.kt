package com.project.myapplication.views.travel.view

import android.os.Bundle
import android.view.View
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.databinding.FragmentTravelDiaryBinding
import com.project.myapplication.views.travel.viewmodel.TravelViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class TravelDiaryFragment: BaseFragment<FragmentTravelDiaryBinding, TravelViewModel>() {
    override val layoutResourceId: Int = R.layout.fragment_travel_diary
    override val thisViewModel: TravelViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {

    }

    override fun initObserve() {

    }
}