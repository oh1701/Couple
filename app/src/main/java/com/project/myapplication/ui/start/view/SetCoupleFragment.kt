package com.project.myapplication.ui.start.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.utils.observer.EventObserver
import com.project.myapplication.ui.dialog.view.WarningDialogFragment
import com.project.myapplication.utils.PhotoClass
import com.project.myapplication.databinding.FragmentSetcoupleBinding
import com.project.myapplication.ui.main.MainViewModel
import com.project.myapplication.ui.start.viewmodel.SetCoupleViewModel
import com.project.myapplication.utils.Datepicker
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/** 커플 정보를 입력하는 프래그먼트 */

class SetCoupleFragment:BaseFragment<FragmentSetcoupleBinding, SetCoupleViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_setcouple
    override val thisViewModel: SetCoupleViewModel by viewModel()
    private val sharedActivityViewModel: MainViewModel by sharedViewModel()
    private val photoClass: PhotoClass by inject()
    private val customDialog: WarningDialogFragment by inject()
    private lateinit var startForResultAlbum: ActivityResultLauncher<Intent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startActivityForResult()
    }

    override fun initView() {
        binding.setcoupleViewModel = thisViewModel
        binding.setcoupleFragment = this
        binding.mainActivityViewModel = sharedActivityViewModel

        thisViewModel.getCoupleSetting(sharedActivityViewModel.onClickView.value!!)
    }

    override fun initObserve() {
        thisViewModel.complete.observe(viewLifecycleOwner) {
            sharedActivityViewModel.settingUpdate()
            requireActivity().onBackPressed()
        }

        thisViewModel.setImageClick.observe(viewLifecycleOwner, EventObserver {
            setImageClick()
        })

        thisViewModel.setBirthClick.observe(viewLifecycleOwner, EventObserver {
            Datepicker(requireContext()).datePicker(thisViewModel)
        })
    }

    private fun setImageClick() {
        if (cameraCheck(requireContext())) { // 퍼미션 체크
            startForResultAlbum.launch(photoClass.albumPictureIntent())
        } else { // 설정창 이동하는 다이얼로그 클래스 불러오기
            WarningDialogFragment().show(supportFragmentManager, "Permission")
        }
    }

    private fun startActivityForResult() {
        startForResultAlbum = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            photoClass.albumPictureResult(result, thisViewModel)
        }
    }
}