package com.project.myapplication.ui.travel.viewpager

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.viewpager2.widget.ViewPager2
import com.project.myapplication.R
import com.project.myapplication.adapter.viewpager_adapter.ViewPagerFullScreenImageAdapter
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.databinding.FragmentViewpagerFullscreenImageBinding
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ViewPagerFullScreenImageFragment():BaseFragment<FragmentViewpagerFullscreenImageBinding, BaseViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_viewpager_fullscreen_image
    override val thisViewModel: BaseViewModel by viewModel()
    private lateinit var diaryOnBackPressed:OnBackPressedCallback

    override fun initView() {
        binding.fullscreenViewPagerImage.adapter = ViewPagerFullScreenImageAdapter(this, arguments?.getStringArrayList(image))
        binding.fullscreenViewPagerImage.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.fullscreenViewPagerImage.setCurrentItem(arguments?.getInt(count) ?: 0, false)
        binding.fullscreenViewPagerImage.offscreenPageLimit = 1
        binding.indicator.attachToPager(binding.fullscreenViewPagerImage)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)

        diaryOnBackPressed = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                supportFragmentManager.popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, diaryOnBackPressed)
    }

    override fun initObserve() {

    }

    override fun onDetach() {
        super.onDetach()
        diaryOnBackPressed.remove()
    }

    companion object{
        private const val count = "count"
        private const val image = "image"

        fun newInstance(countIndex: Int, imagelist:ArrayList<String>): ViewPagerFullScreenImageFragment {
            val f = ViewPagerFullScreenImageFragment()

            val args = Bundle()
            args.putInt(count, countIndex)
            args.putStringArrayList(image, imagelist)
            f.arguments = args

            return f
        }
    }
}