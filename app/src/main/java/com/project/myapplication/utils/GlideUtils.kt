package com.project.myapplication.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.project.myapplication.googlemap.GoogleMapSetting

/** Create By Gyu Seong Oh. 2021 / 11  */

class GlideUtils(private val context: Context){
    fun <Type, V, mapMarker> glideListener(view: View, image:Type, intoView:V, getmarker:mapMarker?, tag:String){ // 제너릭 타입을 통해 타입 미지정
        Glide.with(view)
            .load(image)
            .circleCrop()
            .listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e("onLoadFailed", "onLoadFailed")

                    markerSeticon(getmarker, view, tag)
                    return false
                }
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e("onResourceReady", "onResourceReady")

                    when(intoView){
                        is ImageView -> intoView.setImageDrawable(resource)
                        is ImageButton -> intoView.setImageDrawable(resource)
                    }
                    markerSeticon(getmarker, view, tag)

                    return false
                }
            })
            .into(intoView)
    }

    private fun createDrawableFromView(v: View): Bitmap { // 뷰를 마커로 출력해주기 위한 함수
        v.measure(v.width, v.height)
        val b = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        v.layout(0, 0, v.measuredWidth, v.measuredHeight)
        v.draw(c)
        return b
    }

    private fun <TranscodeType, V> RequestBuilder<TranscodeType>.into(intoView: V) { // 넣는 뷰 타입 리턴하기
        when(intoView){
            is ImageView -> into(intoView)
            is ImageButton -> into(intoView)
        }
    }

    private fun<getMarker> markerSeticon(getmarker:getMarker, view:View, tag:String){
        getmarker?.let{ mymarker -> // 마커가 Null이 아니면 설정해줌.
            if(mymarker is Marker){
                mymarker
                    .setIcon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(view)))
                when(tag){
                    "user" -> mymarker.zIndex = 1.5f
                    "cluster" -> mymarker.zIndex = 1.0f
                }
            }
            else if(mymarker is MarkerOptions){ // Cluster로 온것들
                mymarker
                    .zIndex(1.0f)
                    .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(view)))
            }
        }
    }
}
