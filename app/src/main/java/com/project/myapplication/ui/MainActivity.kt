package com.project.myapplication.ui

import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.project.myapplication.R
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.common.MoveFragment
import com.project.myapplication.common.PermissionCheck
import com.project.myapplication.databinding.ActivityMainBinding
import com.project.myapplication.ui.start.view.StartFragment
import com.project.myapplication.ui.travel.view.TravelActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.system.exitProcess

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
            toast("종료함")
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