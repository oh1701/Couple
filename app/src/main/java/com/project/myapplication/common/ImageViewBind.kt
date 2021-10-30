package com.project.myapplication.common

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.net.URI

object ImageViewBind {
    @BindingAdapter(value = ["imageFromUri"])
    fun ImageView.setImageUri(uri: Uri, context: Context){
        Glide.with(context).load(uri).circleCrop().into(this)
    }
}