package com.project.myapplication.ui.start.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.common.MoveFragment
import com.project.myapplication.common.PhotoFilePath
import com.project.myapplication.databinding.FragmentStartBinding
import com.project.myapplication.ui.MainActivity
import com.project.myapplication.ui.MainRepository
import com.project.myapplication.ui.MainViewModel
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
    private val photoFilePath: PhotoFilePath by inject()
    private lateinit var startForResultAlbum: ActivityResultLauncher<Intent>
    private lateinit var startForResultCamera: ActivityResultLauncher<Uri>
    private lateinit var cameraFileUri: Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startActivityForResult() // oncreate 선언.
    }

    override fun initObserve() {
        thisViewModel.myDatetime.observe(viewLifecycleOwner, {
            binding.coupleText.text = it
        })

        sharedActivityViewModel.settingUpdate.observe(this){
            thisViewModel.getCoupleSetting()
        }
    }

    override fun initView() {
        binding.startFragemnt = this
        binding.startViewModel = thisViewModel

        thisViewModel.getmyDatetime()
        thisViewModel.getCoupleSetting()

        binding.coupleImage1.setOnClickListener { // 카메라 권한 확인하기.
//            cameraFileUri = photoFilePath.getImage()
//            startForResultCamera.launch(cameraFileUri)
        }

        binding.coupleImage2.setOnClickListener {
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = MediaStore.Images.Media.CONTENT_TYPE
//            intent.type = "image/*"
//            startForResultAlbum.launch(intent)
        }
    }

    fun openMaindrawer(){
        (requireActivity() as MainActivity).openDrawer()
    }

    fun travelIntent(){
        val intent = Intent(requireActivity(), TravelActivity()::class.java)
        startActivity(intent)
    }

    fun settingCouple(number : Int){
        sharedActivityViewModel.settingClickView(number)

        moveFragment
            .addFragmentUp(supportFragmentManager, SetCoupleFragment(), R.id.inside_fragment)
            .addToBackStack("start")
            .commit()
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