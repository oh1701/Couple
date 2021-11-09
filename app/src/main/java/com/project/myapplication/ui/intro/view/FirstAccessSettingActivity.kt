package com.project.myapplication.ui.intro.view

import android.os.Bundle
import android.util.Log
import com.project.myapplication.R
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.data.sharedpreference.IntroSettingShared
import com.project.myapplication.databinding.ActivityFirstaccessSettingBinding
import com.project.myapplication.di.KoinApplication.Companion.sharedPreference
import com.project.myapplication.ui.MainActivity
import com.project.myapplication.ui.intro.viewmodel.FirstAccessSettingViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

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
            sharedPreference.setIntroCoupleDateSetting(true) // 재설정시 메뉴 창에서 설정해달라는 말하기.
            sharedPreference.setCoupleDate(thisViewModel.saveCoupleDate.value!!)
            moveActivity(MainActivity::class.java)
        }
    }
}