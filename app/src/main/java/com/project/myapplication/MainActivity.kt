package com.project.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
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

        binding.diary.setOnClickListener {
            toast("123")
        }
    }

    override fun initObserve() {
        thisviewModel.myDatetime.observe(this, {
            var span = SpannableStringBuilder("우리 사랑\n벌써 ${it}일")
            span.setSpan(RelativeSizeSpan(0.6f), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.coupleText.text = span
        })
    }
}