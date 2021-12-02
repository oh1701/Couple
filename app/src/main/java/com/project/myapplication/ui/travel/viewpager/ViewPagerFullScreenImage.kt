package com.project.myapplication.ui.travel.viewpager

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.databinding.FragmentViewpagerFullscreenImageBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ViewPagerFullScreenImage(private val image:ArrayList<String>?, private val count:Int?):BaseFragment<FragmentViewpagerFullscreenImageBinding, BaseViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_viewpager_fullscreen_image
    override val thisViewModel: BaseViewModel by viewModel()
    private lateinit var diaryOnBackPressed:OnBackPressedCallback

    override fun initView() {
        binding.fullscreenViewPagerImage.adapter = FullScreenImageAdapter(this, image)
        binding.fullscreenViewPagerImage.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.fullscreenViewPagerImage.setCurrentItem(count ?: 0, false)
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
}

class FullScreenImageAdapter(private val fragment: Fragment,
                             private val image: ArrayList<String>?) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return when (image?.size) {
            null, 0 -> 1
            else -> image.size
        }
    }

    override fun createFragment(position: Int): Fragment { // 현재 포지션에 따라 보여줄 프래그먼트
        return when (image?.size) {
            null, 0 -> ViewPagerImageAdapter(null, fragment)
            else -> ViewPagerImageAdapter(image[position], fragment)
        }
    }
}