package com.project.myapplication.ui.intro.view

import android.os.Bundle
import com.project.myapplication.ui.MainActivity
import com.project.myapplication.R
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.databinding.ActivityIntroBinding
import com.project.myapplication.ui.intro.viewmodel.IntroViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class IntroActivity : BaseActivity<ActivityIntroBinding, IntroViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_intro
    override val thisViewModel: IntroViewModel by viewModel()

    override fun initView() {
        thisViewModel.timer()
    }

    override fun initObserve() {
        thisViewModel.timerComplete.observe(this){
            moveActivity(FirstAccessSettingActivity::class.java)
//            moveActivity(MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }

}