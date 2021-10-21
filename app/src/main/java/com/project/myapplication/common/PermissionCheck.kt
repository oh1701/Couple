package com.project.myapplication.common

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import java.util.jar.Manifest

class PermissionCheck(private val activity: Activity) {
    private fun checkSelfPermission(permission : String): Boolean{
        return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun cameraCheck(){
        if(!checkSelfPermission(android.Manifest.permission.CAMERA)){
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(android.Manifest.permission.CAMERA),
                1000
            )
        }
    }

    fun locationCheck(){
        if(!checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) || !checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION),
                1001
            )
        }
    }

    fun allPermissionCheck(){
        if(!checkSelfPermission(android.Manifest.permission.CAMERA) || !checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) || !checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(android.Manifest.permission.CAMERA,android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION),
                1005
            )
        }
    }
}