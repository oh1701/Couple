package com.project.myapplication.bind

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.project.myapplication.common.CheckSelfPermission

object ImageViewBind:CheckSelfPermission {
    @JvmStatic
    @BindingAdapter("imageFromUri")
    fun setImageUri(view: ImageView, uri: String?){
        if(uri != null && uri != "null") {
            Glide.with(view).load(Uri.parse(uri)).transform(CenterCrop(), RoundedCorners(40)).into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("coupleImage")
    fun ImageView.coupleImage(uri: String?){
        if(uri != null && uri != "null"){
            Glide.with(this).load(Uri.parse(uri)).circleCrop().into(this)
        }
        Log.e("uri는", uri.toString())
    }
}