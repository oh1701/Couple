package com.project.myapplication.travel

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.project.myapplication.R
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.common.PermissionCheck
import com.project.myapplication.data.PhotoFilePath
import com.project.myapplication.databinding.FragmentStartBinding
import org.koin.android.ext.android.inject

class FragmentCall: BaseFragment<FragmentStartBinding, FragementViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_start
    override val thisViewModel: FragementViewModel by inject()
    private val photoFilePath: PhotoFilePath by inject()
    private lateinit var startForResultAlbum: ActivityResultLauncher<Intent>
    private lateinit var startForResultCamera: ActivityResultLauncher<Uri>
    private lateinit var cameraFileUri: Uri


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startActivityForResult() // oncreate 선언.
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun initObserve() {
        thisViewModel.myDatetime.observe(viewLifecycleOwner, {
            binding.coupleText.text = it
        })
    }

    override fun initView() {
        thisViewModel.getmyDatetime()
        binding.coupleImage1.setOnClickListener {
            cameraFileUri = photoFilePath.getImage()
            startForResultCamera.launch(cameraFileUri)
        }

        binding.coupleImage2.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.type = "image/*"
            startForResultAlbum.launch(intent)
        }
    }

    private fun startActivityForResult(){
        //해당 함수는 oncreate에서 선언해줘야함.
        startForResultAlbum = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == Activity.RESULT_OK) {
                Glide.with(this).load(result.data?.data).circleCrop().into(binding.coupleImage2)
            }
        }

        startForResultCamera = registerForActivityResult(ActivityResultContracts.TakePicture()){ check ->
            if(check) {
                Glide.with(this).load(cameraFileUri).circleCrop().into(binding.coupleImage1)
            }
        }
    }
}