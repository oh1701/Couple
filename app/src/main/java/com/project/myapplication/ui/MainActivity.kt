package com.project.myapplication.ui

import android.os.Bundle
import androidx.core.view.GravityCompat
import com.project.myapplication.R
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.databinding.ActivityMainBinding
import com.project.myapplication.ui.start.view.StartFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main
    override val thisViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        allPermissionCheck(this) // 권한 요청
        startFragment()
    }

    override fun initView() {
    }

    override fun initObserve() {
    }

    fun openDrawer(){
        binding.mainDrawer.openDrawer(GravityCompat.END)
    }

    override fun onBackPressed() {
        if(binding.mainDrawer.isDrawerOpen(GravityCompat.END)){
            binding.mainDrawer.closeDrawers()
        }
        else{
            super.onBackPressed()
        }
    }

    private fun startFragment(){
        this.supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(
                R.id.inside_fragment,
                StartFragment()
            ).commit()
    }
}