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
import com.project.myapplication.ui.start.viewmodel.SetCoupleViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class SetCoupleFragment:BaseFragment<FragmentSetcoupleBinding, SetCoupleViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_setcouple
    override val thisViewModel: SetCoupleViewModel by viewModel()
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
    }

    override fun initObserve() {
    }

    fun setImageClick(){
        cameraFileUri = photoFilePath.getImage()
        startForResultCamera.launch(cameraFileUri)
    }

    fun datePicker(){
        val today = GregorianCalendar()
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DATE)

        DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, p1: Int, p2: Int, p3: Int) {
                val year = p1
                val month = p2 + 1
                val day = p3

                thisViewModel.setBirthDay(year, month, day)
            }
        },year,month,day)
            .apply{
                datePicker.maxDate = Calendar.getInstance().timeInMillis
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