package com.project.myapplication

import android.os.Bundle
import androidx.core.view.GravityCompat
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.common.MoveFragment
import com.project.myapplication.common.PermissionCheck
import com.project.myapplication.databinding.ActivityMainBinding
import com.project.myapplication.start.StartFragment
import com.project.myapplication.travel.TravelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main
    override val thisViewModel: MainViewModel by viewModel()
    private val permissionCheck = PermissionCheck(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionCheck.allPermissionCheck() // 권한 요청
        startFragment()
    }

    override fun initView() {
        binding.main.setOnClickListener {
            MoveFragment().moveFragment(this.supportFragmentManager, StartFragment())
        }

        binding.travel.setOnClickListener {
            MoveFragment().moveFragment(this.supportFragmentManager, TravelFragment())
        }

        binding.menu.setOnClickListener {
            binding.mainDrawer.openDrawer(GravityCompat.END)
        }

        binding.diary.setOnClickListener {
        }
    }

    override fun initObserve() {
    }

    private fun startFragment(){
        this.supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right)
            .replace(
                R.id.inside_fragment,
                StartFragment()
            ).commit()
    }
}