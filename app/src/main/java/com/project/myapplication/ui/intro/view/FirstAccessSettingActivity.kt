package com.project.myapplication.ui.intro.view

import android.os.Bundle
import com.project.myapplication.R
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.data.sharedpreference.IntroSettingShared
import com.project.myapplication.databinding.ActivityFirstaccessSettingBinding
import com.project.myapplication.ui.MainActivity
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

/** 앱에 처음 진입하였을 때 사귀게 된 날짜를 물어보는 클래스.*/

class FirstAccessSettingActivity:BaseActivity<ActivityFirstaccessSettingBinding, BaseViewModel>() {
    override val layoutResourceId: Int = R.layout.activity_firstaccess_setting
    override val thisViewModel: BaseViewModel by viewModel()
    private val sharedPreference:IntroSettingShared by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        binding.saveCoupleday.setOnClickListener {
            sharedPreference.setIntroCoupleDateSetting(true) // 재설정시 메뉴 창에서 설정해달라는 말하기.
            moveActivity(MainActivity::class.java)
        }
    }

    override fun initObserve() {

    }
}