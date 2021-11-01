package com.project.myapplication.ui.travel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.project.myapplication.R


/** 맵 패키지 안에 마커 , 카메라 부분 등등으로 나누기*/
class GoogleMapSetting(val context: Context, private val googleMap: GoogleMap) {
    private lateinit var userMarker:Marker
    private lateinit var marker:Marker
    private lateinit var userMarkerImage:BitmapDescriptor
    private lateinit var markerView:View
    private var cameraMoving = true

    fun mapSetting(){
        googleMap.uiSettings.isTiltGesturesEnabled = false
        googleMap.uiSettings.isRotateGesturesEnabled = false
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        googleMap.isBuildingsEnabled = false
    }

    fun repeatFunction(latlng: LatLng){
        animateCamera(latlng)
        userCreateMarker(latlng)
    }

    private fun animateCamera(latlng: LatLng){
        if(cameraMoving) {
            val cameraPosition = CameraPosition.Builder()
                .target(latlng)
                .zoom(17f)
                .build()

            googleMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(cameraPosition)
            )
        }
    }

    private fun onOffCameraAnimate(){ // 마커 따라가는 카메라 움직임을 onOff하는 함수.
        cameraMoving = !cameraMoving
    }

    private fun userCreateMarker(latlng: LatLng){
        if(::userMarker.isInitialized){
//            마커가 존재할 시, addMarker말고 움직임으로 표현하기.
            Log.e("마커 존재함", "마커 존재함")
        }
        else{
            val view = getMarkerView()

            Glide.with(context).load(R.drawable.natur).circleCrop().listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    val image = view!!.findViewById<ImageView>(R.id.profile_image)
                    image.setImageDrawable(resource)
                    userMarkerImage = BitmapDescriptorFactory.fromBitmap(createDrawableFromView(view))
                    userMarker = googleMap.addMarker(
                        MarkerOptions()
                            .position(latlng)
                            .title("user")
                            .zIndex(5.0f)
                            .icon(userMarkerImage)
                    )!!
                    return true
                }
            }).into(view!!.findViewById(R.id.profile_image))
        }
    }

    fun addDiaryMarker(latlng: LatLng){
        marker = googleMap.addMarker(MarkerOptions()
            .position(latlng)
            .zIndex(1.0f))!!
        Log.e("마커 생성", "생성하였음.")
    }

    private fun createDrawableFromView(v: View): Bitmap { // 뷰를 마커로 출력해주기 위한 함수
        v.measure(v.width, v.height)
        val b = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        v.layout(0, 0, v.measuredWidth, v.measuredHeight)
        v.draw(c)
        return b
    }

    private fun getMarkerView(): View? {
        return LayoutInflater.from(context).inflate(R.layout.marker_diaryview, null)
    }
}