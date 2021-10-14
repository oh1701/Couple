package com.project.myapplication.data

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File

class PhotoFilePath(private val context: Context) {
    private fun createFile(fileName: String): File {
        val mediaStorageDir = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) ,
            "APP_TAG"
        )
        return File(mediaStorageDir.path + File.separator.toString() + fileName)
    }

    fun getImage(): Uri {
        val photoFile = createFile("date.jpg") //date.jpg 라는 이름을 가진 파일을 찾습니다.
        return FileProvider.getUriForFile(
            context ,
            "com.project.myapplication" ,
            photoFile
        ) // 해당 파일의 Uri를 가져옵니다.
    }
}