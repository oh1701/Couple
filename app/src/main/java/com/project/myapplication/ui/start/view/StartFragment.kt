package com.project.myapplication.ui.start.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.utils.PhotoClass
import com.project.myapplication.databinding.FragmentStartBinding
import com.project.myapplication.ui.main.MainActivity
import com.project.myapplication.ui.main.MainViewModel
import com.project.myapplication.ui.start.viewmodel.StartViewModel
import com.project.myapplication.ui.travel.view.TravelActivity
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class StartFragment: BaseFragment<FragmentStartBinding, StartViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_start
    override val thisViewModel: StartViewModel by viewModel()
    private val sharedActivityViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initObserve() {
    }

    override fun sharedObserve() {
        super.sharedObserve()

        sharedActivityViewModel.settingUpdate.observe(viewLifecycleOwner){
            thisViewModel.getCoupleSetting()
        }
    }

    override fun initView() {
        binding.startFragemnt = this
        binding.startViewModel = thisViewModel

        thisViewModel.getmyDatetime()
        thisViewModel.getCoupleSetting()
    }

    fun openMaindrawer(){
        (requireActivity() as MainActivity).openDrawer()
    }

    fun travelIntent(){
        val intent = Intent(requireActivity(), TravelActivity()::class.java)
        startActivity(intent)
    }

    fun settingCouple(number : Int){
        sharedActivityViewModel.coupleImageClick(number)
        moveFragment
            .addFragmentUp(supportFragmentManager, SetCoupleFragment(), R.id.inside_fragment, null)
            .addToBackStack("start")
            .commit()
    }
}