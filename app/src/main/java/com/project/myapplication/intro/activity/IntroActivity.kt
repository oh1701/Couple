package com.project.myapplication.intro.activity

import android.os.Bundle
import com.project.myapplication.R
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.databinding.ActivityIntroBinding
import com.project.myapplication.intro.repository.IntroRepository
import com.project.myapplication.intro.viewmodel.IntroViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class IntroActivity : BaseActivity<ActivityIntroBinding, IntroViewModel, IntroRepository>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_intro
    override val thisviewModel: IntroViewModel by viewModel()

    override fun initView() {
    }

    override fun initObserve() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }

}