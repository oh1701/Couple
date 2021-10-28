package com.project.myapplication.ui.travel.view

import com.project.myapplication.R
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.databinding.ActivityTravelBinding
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TravelActivity:BaseActivity<ActivityTravelBinding, TravelViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_travel
    override val thisViewModel: TravelViewModel by viewModel()

    override fun initView() {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_layout,
            TravelMapFragment()
        ).commit()
    }

    override fun initObserve() {

    }
}