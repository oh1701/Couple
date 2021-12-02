package com.project.myapplication.ui

import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.databinding.ActivityViewpagerBinding
import com.project.myapplication.ui.travel.view.TravelDiaryFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.ext.getScopeName

class Viewpagerrrr(private val image:String?, private val fragment: Fragment):BaseFragment<ActivityViewpagerBinding, BaseViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_viewpager
    override val thisViewModel: BaseViewModel by viewModel()

    override fun initView() {
        if (image != null)
            Glide.with(this).load(Uri.parse(image)).transform(CenterCrop(), RoundedCorners(40)).into(binding.viewPagerImage)

        binding.viewPagerImage.setOnClickListener {
            if(fragment is TravelDiaryFragment) {
                fragment.cameraOpen()
            }
        }
    }

    override fun initObserve() {

    }
}