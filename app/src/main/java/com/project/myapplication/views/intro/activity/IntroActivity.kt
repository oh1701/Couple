package com.project.myapplication.views.intro.activity

import android.os.Bundle
import com.project.myapplication.views.MainActivity
import com.project.myapplication.R
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.databinding.ActivityIntroBinding
import com.project.myapplication.views.intro.viewmodel.IntroViewModel
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
        timer()
    }

    override fun initObserve() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }

    private fun timer(){
        val timer = Observable.timer(1500L, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(){
                moveActivity(MainActivity::class.java)
            }

        compositeDisposable.add(timer)
    }
}