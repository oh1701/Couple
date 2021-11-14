package com.project.myapplication.common

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

/** 권한 부여 유무를 파악하는 인터페이스 */

interface CheckSelfPermission {
    private fun checkSelfPermission(context: Context, permission : String): Boolean{
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun cameraCheck(context: Context):Boolean{ // 카메라 퍼미션
        return checkSelfPermission(context, android.Manifest.permission.CAMERA)
    }

    fun locationCheck(context: Context): Boolean{ // Location 퍼미션
        return checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) &&
                checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    fun allPermissionCheck(context: Context){ //Camera, Location 권한 확인. 권한이 안되어 있으면 권한 요청 팝업 띄우기.
        if(!checkSelfPermission(context, android.Manifest.permission.CAMERA) ||
            !checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) ||
            !checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(
                (context as Activity),
                arrayOf(android.Manifest.permission.CAMERA,android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION),
                1005
            )
        }
    }
}