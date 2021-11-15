package com.project.myapplication.ui.intro.view

import android.os.Bundle
import com.project.myapplication.R
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.databinding.ActivityFirstaccessSettingBinding
import com.project.myapplication.ui.MainActivity
import com.project.myapplication.ui.intro.viewmodel.FirstAccessSettingViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/** 앱에 처음 진입하였을 때 사귀게 된 날짜를 물어보는 클래스.*/

class FirstAccessSettingActivity:BaseActivity<ActivityFirstaccessSettingBinding, FirstAccessSettingViewModel>() {
    override val layoutResourceId: Int = R.layout.activity_firstaccess_setting
    override val thisViewModel: FirstAccessSettingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        binding.firstAccessActivity = this
        binding.firstAccessViewModel = thisViewModel
    }

    override fun initObserve() {
        thisViewModel.saveCoupleDateButton.observe(this){
            moveActivity(MainActivity::class.java)
        }
    }
}