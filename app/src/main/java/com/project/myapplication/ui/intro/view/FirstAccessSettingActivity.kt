package com.project.myapplication.ui.intro.view

import android.os.Bundle
import com.project.myapplication.R
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.databinding.ActivityFirstaccessSettingBinding
import com.project.myapplication.ui.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class FirstAccessSettingActivity:BaseActivity<ActivityFirstaccessSettingBinding, BaseViewModel>() {
    override val layoutResourceId: Int = R.layout.activity_firstaccess_setting
    override val thisViewModel: BaseViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        binding.saveCoupleday.setOnClickListener {
            // 재설정시 메뉴 창에서 설정해달라는 말하기.
            moveActivity(MainActivity::class.java)
        }
    }

    override fun initObserve() {

    }
}