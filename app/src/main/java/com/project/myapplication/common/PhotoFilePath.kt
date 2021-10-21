package com.project.myapplication.common

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File

class PhotoFilePath(private val context: Context) {
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
}