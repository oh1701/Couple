package com.project.myapplication.common

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import java.util.jar.Manifest

class PermissionCheck(private val context: Context) {
    fun cameraCheck(activity: Activity){
        if(ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(android.Manifest.permission.CAMERA),
                1000
            )
        }
    }
}