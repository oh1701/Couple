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