package com.project.myapplication.intro.activity

import android.os.Bundle
import com.project.myapplication.MainActivity
import com.project.myapplication.Map
import com.project.myapplication.R
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.databinding.ActivityIntroBinding
import com.project.myapplication.intro.viewmodel.IntroViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class IntroActivity : BaseActivity<ActivityIntroBinding, IntroViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_intro
    override val thisviewModel: IntroViewModel by viewModel()

    override fun initView() {
        thisviewModel.timeCheck()
    }

    override fun initObserve() {
        thisviewModel.moveActivity.observe(this, {
            moveActivity(MainActivity::class.java)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }

}