package com.project.myapplication.ui.intro.view

import android.os.Bundle
import com.project.myapplication.R
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.databinding.ActivityIntroBinding
import com.project.myapplication.ui.MainActivity
import com.project.myapplication.ui.intro.viewmodel.IntroViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/** 앱에 처음 진입하였을 때 나오는 스플래시 화면, 1.5초후 액티비티 이동 */

class IntroActivity : BaseActivity<ActivityIntroBinding, IntroViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_intro
    override val thisViewModel: IntroViewModel by viewModel()

    override fun initView() {
        thisViewModel.timer()
    }

    override fun initObserve() {
        thisViewModel.checkCoupleDateSetting.observe(this){
            it.getContentIfNotHandled()?.let{ event ->
                if(event) {
                    moveActivity(MainActivity::class.java)
                }
                else {
                    moveActivity(FirstAccessSettingActivity::class.java)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }

}