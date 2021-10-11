package com.project.myapplication

import android.content.Intent
import android.os.Bundle
import com.project.myapplication.base.BaseActivity
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
    }

    override fun initObserve() {

    }
}