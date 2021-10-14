package com.project.myapplication

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.project.myapplication.base.BaseActivity
import com.project.myapplication.base.BaseRepository
import com.project.myapplication.base.PermissionCheck
import com.project.myapplication.data.PhotoFilePath
import com.project.myapplication.databinding.ActivityMainBinding
import com.project.myapplication.di.MyModules
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.jar.Manifest

/** 카메라 불러오기 안되는 중. */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main
    override val thisviewModel: MainViewModel by viewModel()
    private val permissionCheck:PermissionCheck by inject()
    private val photoFilePath: PhotoFilePath by inject()
    private lateinit var startForResultAlbum: ActivityResultLauncher<Intent>
    private lateinit var startForResultCamera: ActivityResultLauncher<Uri>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivityForResult() // oncreate 선언.
        permissionCheck.cameraCheck(this)
    }

    override fun initView() {
        thisviewModel.getmyDatetime()

        binding.diary.setOnClickListener {
            moveActivity(Map::class.java)
        }

        binding.menu.setOnClickListener {
            binding.mainDrawer.openDrawer(GravityCompat.END)
        }

        binding.coupleImage1.setOnClickListener {
            startForResultCamera.launch(photoFilePath.getImage())
        }

        binding.coupleImage2.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.type = "image/*"
            startForResultAlbum.launch(intent)
        }
    }

    override fun initObserve() {
        thisviewModel.myDatetime.observe(this, {
            binding.coupleText.text = it
        })
    }

    private fun startActivityForResult(){
        //해당 함수는 oncreate에서 선언해줘야함.
        startForResultAlbum = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            if(result.resultCode == Activity.RESULT_OK) {
                Glide.with(this).load(result.data?.data).circleCrop().into(binding.coupleImage2)
            }
        }

        startForResultCamera = registerForActivityResult(ActivityResultContracts.TakePicture()){check ->
            if(check) {
                Glide.with(this).load(photoFilePath.getImage()).circleCrop().into(binding.coupleImage1)
            }
            log("확인", check.toString())
        }
    }
}