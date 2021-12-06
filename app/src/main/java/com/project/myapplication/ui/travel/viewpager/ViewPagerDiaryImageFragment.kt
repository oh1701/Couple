package com.project.myapplication.ui.travel.viewpager

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.databinding.FragmentViewpagerImageBinding
import com.project.myapplication.ui.travel.view.TravelDiaryFragment
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
@SuppressLint("ClickableViewAccessibility")
class ViewPagerDiaryImageFragment(private val image:String?, private val fragment: Fragment, private val mytag:String):
    BaseFragment<FragmentViewpagerImageBinding, BaseViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_viewpager_image
    override val thisViewModel: BaseViewModel by viewModel()

    override fun initView() {
        if (image != null && mytag != "fullScreen") {
            Glide.with(this).load(Uri.parse(image)).transform(CenterCrop(), RoundedCorners(40))
                .into(binding.viewPagerImage)
        } else if (image != null && mytag == "fullScreen") {
            Glide.with(this).load(Uri.parse(image)).into(binding.viewPagerImage)
        } else {
            binding.viewPagerImage.scaleType = ImageView.ScaleType.CENTER_INSIDE
        }

        binding.layout.setOnClickListener {
            if (fragment is TravelDiaryFragment) {
                fragment.cameraOpen()
            }
        }
    }

    override fun initObserve() {

    }
}