package com.project.myapplication.common

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.project.myapplication.R
import java.net.URI

object ImageViewBind {
    @JvmStatic
    @BindingAdapter("imageFromUri")
    fun setImageUri(view: ImageView, uri: Uri?){
        if(uri != null) {
            Glide.with(view).load(uri).circleCrop().into(view)
        }
    }
}