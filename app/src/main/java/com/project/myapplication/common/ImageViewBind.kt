package com.project.myapplication.common

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.RoundedCorner
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.project.myapplication.R
import java.net.URI

object ImageViewBind {
    @JvmStatic
    @BindingAdapter("imageFromUri")
    fun setImageUri(view: ImageView, uri: Uri?){
        if(uri != null) {
            Glide.with(view).load(uri).transform(CenterCrop(), RoundedCorners(40)).into(view)
        }
    }
}