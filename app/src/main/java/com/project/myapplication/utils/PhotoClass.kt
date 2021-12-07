package com.project.myapplication.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.project.myapplication.ui.start.viewmodel.SetCoupleViewModel
import com.project.myapplication.ui.start.viewmodel.StartViewModel
import com.project.myapplication.ui.travel.viewmodel.TravelDiaryViewModel
import java.io.File

/** 저장할 이미지 파일 경로 */
/** Create By Gyu Seong Oh. 2021 / 10  */

class PhotoClass(private val context: Context) {
    private var cameraFileUri:Uri? = null

    fun getImage(): Uri {
        val photoFile = File.createTempFile("IMG_",".jpg", context.getExternalFilesDir(Environment.DIRECTORY_PICTURES))
        val file = FileProvider.getUriForFile(
            context ,
            "com.project.myapplication" ,
            photoFile
        ) // 해당 파일의 Uri를 가져옵니다.
        Log.e("경로는", file.toString())
        return file
    }

    fun albumMultipleIntent():Intent{
        val intent = Intent() // 순서에 맞게 가져오기 위해 PICK -> GET_CONTENT 사용하였음.
        intent.type = ("image/*")
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)  // 다중 이미지를 가져올 수 있도록 세팅

        return intent
    }

    fun albumPictureIntent():Intent{
        val intent = Intent()
        intent.type = ("image/*")
        intent.action = Intent.ACTION_GET_CONTENT
//        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        return intent
    }

    fun <T> albumPictureResult(result: ActivityResult, viewModel: T){
        if(result.resultCode == Activity.RESULT_OK) {
            val resultData = result.data

            if(resultData != null){
                when(viewModel){
                    is SetCoupleViewModel -> { viewModel.getUri(resultData)}
                    is TravelDiaryViewModel -> { viewModel.getClipData(resultData)}
                }
            }
            else{
                Toast.makeText(context, "사진을 가져오지 못했습니다.\n다시 한 번 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}