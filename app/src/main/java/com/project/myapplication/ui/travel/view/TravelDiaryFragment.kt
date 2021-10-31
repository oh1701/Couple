package com.project.myapplication.ui.travel.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.base.BaseRepository
import com.project.myapplication.common.PhotoFilePath
import com.project.myapplication.databinding.FragmentTravelDiaryBinding
import com.project.myapplication.ui.travel.repository.TravelDiaryRepository
import com.project.myapplication.ui.travel.viewmodel.TravelDiaryViewModel
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class TravelDiaryFragment: BaseFragment<FragmentTravelDiaryBinding, TravelDiaryViewModel>() {
    override val layoutResourceId: Int = R.layout.fragment_travel_diary
    override val thisViewModel: TravelDiaryViewModel by viewModel()
    private lateinit var startForResultAlbum: ActivityResultLauncher<Intent>
    private lateinit var startForResultCamera: ActivityResultLauncher<Uri>
    private lateinit var cameraFileUri: Uri
    private val photoFilePath: PhotoFilePath by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.travelDiary = thisViewModel
        startActivityForResult() // oncreate 선언.
    }

    override fun initView() {

    }

    override fun initObserve() {
        thisViewModel.imageClick.observe(this, {
            cameraFileUri = photoFilePath.getImage()
            startForResultCamera.launch(cameraFileUri)
        })
    }

    private fun startActivityForResult(){
        //해당 함수는 oncreate에서 선언해줘야함.
        startForResultCamera = registerForActivityResult(ActivityResultContracts.TakePicture()){ check ->
            if(check) {
                thisViewModel.getUri(cameraFileUri)
            }
        }
    }
}