package com.project.myapplication.common

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.net.URI

object ImageViewBind {
    @JvmStatic
    @BindingAdapter("imageFromUri")
    fun setImageUri(view: ImageView, uri: Uri?){
        Glide.with(view).load(uri).circleCrop().into(view)
        Log.e("확인", uri.toString())
    }
}