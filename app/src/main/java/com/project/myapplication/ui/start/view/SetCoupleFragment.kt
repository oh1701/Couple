package com.project.myapplication.ui.start.view

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.utils.EventObserver
import com.project.myapplication.ui.dialog.view.WarningDialogFragment
import com.project.myapplication.utils.PhotoFilePath
import com.project.myapplication.databinding.FragmentSetcoupleBinding
import com.project.myapplication.ui.MainViewModel
import com.project.myapplication.ui.start.viewmodel.SetCoupleViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

/** 커플 정보를 입력하는 프래그먼트 */

class SetCoupleFragment:BaseFragment<FragmentSetcoupleBinding, SetCoupleViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_setcouple
    override val thisViewModel: SetCoupleViewModel by viewModel()
    private val sharedActivityViewModel: MainViewModel by sharedViewModel()
    private val photoFilePath: PhotoFilePath by inject()
    private val customDialog: WarningDialogFragment by inject()
    private lateinit var startForResultAlbum: ActivityResultLauncher<Intent>
    private lateinit var startForResultCamera: ActivityResultLauncher<Uri>
    private lateinit var cameraFileUri: Uri

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
        thisViewModel.complete.observe(viewLifecycleOwner){
            sharedActivityViewModel.settingUpdate()
            requireActivity().onBackPressed()
        }

        thisViewModel.setImageClick.observe(viewLifecycleOwner, EventObserver{
                setImageClick()
        })

        thisViewModel.setBirthClick.observe(viewLifecycleOwner, EventObserver{
                datePicker()
        })
    }

    private fun setImageClick(){
        if(cameraCheck(requireContext())){
            cameraFileUri = photoFilePath.getImage()
            startForResultCamera.launch(cameraFileUri)
        }
        else{ // 설정창 이동하는 다이얼로그 클래스 불러오기
            WarningDialogFragment().show(supportFragmentManager, "Permission")
        }
    }

    private fun datePicker(){
        val today = GregorianCalendar()
        val todayYear = today.get(Calendar.YEAR)
        val todayMonth = today.get(Calendar.MONTH)
        val todayDay = today.get(Calendar.DATE)

        DatePickerDialog(requireContext(), { _, year, month, day ->
                thisViewModel.setBirthDay(year, month + 1, day)
            },todayYear,todayMonth,todayDay)
            .apply{
                this.datePicker.maxDate = Calendar.getInstance().timeInMillis
            }
            .show()
    }

    private fun startActivityForResult(){
        startForResultCamera = registerForActivityResult(ActivityResultContracts.TakePicture()){
            if(it){
                thisViewModel.getUri(cameraFileUri)
            }
        }
    }
}