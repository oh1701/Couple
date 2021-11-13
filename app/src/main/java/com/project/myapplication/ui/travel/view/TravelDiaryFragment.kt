package com.project.myapplication.ui.travel.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.common.Event
import com.project.myapplication.common.EventObserver
import com.project.myapplication.common.PhotoFilePath
import com.project.myapplication.databinding.FragmentTravelDiaryBinding
import com.project.myapplication.ui.travel.viewmodel.TravelDiaryViewModel
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class TravelDiaryFragment(): BaseFragment<FragmentTravelDiaryBinding, TravelDiaryViewModel>() {
    override val layoutResourceId: Int = R.layout.fragment_travel_diary
    override val thisViewModel: TravelDiaryViewModel by viewModel()
    private val sharedActivityViewModel: TravelViewModel by sharedViewModel()
    private lateinit var startForResultAlbum: ActivityResultLauncher<Intent>
    private lateinit var startForResultCamera: ActivityResultLauncher<Uri>
    private lateinit var cameraFileUri: Uri
    private val photoFilePath: PhotoFilePath by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startActivityForResult() // oncreate 선언.
    }

    override fun initView() {
        binding.travelDiaryViewModel = thisViewModel
        binding.travelMainViewModel = sharedActivityViewModel
        binding.travelDiaryFragment = this

        thisViewModel.createDiarysetting()
    }

    override fun initObserve() {
        thisViewModel.toastLiveData.observe(this, {event : Event<String> ->
            Toast.makeText(requireContext(), event.peekContent(), Toast.LENGTH_SHORT).show()
        })
    }

    fun cameraOpen(){
        cameraFileUri = photoFilePath.getImage()
        startForResultCamera.launch(cameraFileUri)
    }

    fun completeDiarycreate(){
        sharedActivityViewModel.myLocationLatLng.value?.let { thisViewModel.createDiary(it) }
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