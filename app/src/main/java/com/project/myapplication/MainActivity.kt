package com.project.myapplication

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.base.BaseRepository
import com.project.myapplication.databinding.ActivityMainBinding
import com.project.myapplication.di.MyModules
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main
    override val thisviewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        thisviewModel.getmyDatetime()
    }

    override fun initObserve() {
        thisviewModel.myDatetime.observe(this, {
            binding.coupleText.text = "우리가 서로 만난 지\n 벌써,\n ${it}일 째"
        })
    }
}