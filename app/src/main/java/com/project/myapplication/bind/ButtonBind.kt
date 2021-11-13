package com.project.myapplication.bind

import android.widget.Button
import androidx.databinding.BindingAdapter
import com.google.android.gms.maps.model.LatLng

object ButtonBind {
    @JvmStatic
    @BindingAdapter("CustomOnclickListener")
    fun Button.onClickCustomButton(latLng: LatLng){

    }
}