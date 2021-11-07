package com.project.myapplication.bind

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

object ImageViewBind {
    @JvmStatic
    @BindingAdapter("imageFromUri")
    fun setImageUri(view: ImageView, uri: Uri?){
        if(uri != null) {
            Glide.with(view).load(uri).transform(CenterCrop(), RoundedCorners(40)).into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("coupleImage")
    fun ImageView.coupleImage(uri: Uri?){
        if(uri != null){
            Glide.with(this).load(uri).circleCrop().into(this)
        }
        Log.e("uri는", uri.toString())
    }
}