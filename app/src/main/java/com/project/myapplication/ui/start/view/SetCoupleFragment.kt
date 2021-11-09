package com.project.myapplication.ui.start.view

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.common.PhotoFilePath
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
    private lateinit var startForResultAlbum: ActivityResultLauncher<Intent>
    private lateinit var startForResultCamera: ActivityResultLauncher<Uri>
    private lateinit var cameraFileUri: Uri
    private val photoFilePath: PhotoFilePath by inject()

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
        thisViewModel.complete.observe(this){
            sharedActivityViewModel.settingUpdate()
            requireActivity().onBackPressed()
        }
    }

    fun setImageClick(){
        cameraFileUri = photoFilePath.getImage()
        startForResultCamera.launch(cameraFileUri)
    }

    fun datePicker(){
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