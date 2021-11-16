package com.project.myapplication.googlemap

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
    private lateinit var userMarkerImage:BitmapDescriptor
    private lateinit var markerView:View
    private var cameraMoving = true

    // Setting

    fun mapSetting(){
        googleMap.uiSettings.isTiltGesturesEnabled = false
        googleMap.uiSettings.isRotateGesturesEnabled = false
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        googleMap.isBuildingsEnabled = false
    }

    // Camera

    fun repeatFunction(latlng: LatLng){
        animateCamera(latlng)
        userCreateMarker(latlng)
    }

    fun settingCamera(set:Boolean){
        cameraMoving = set
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

    // Marker

    private fun userCreateMarker(latlng: LatLng){
        Log.e("유저마커", latlng.toString())
        if(::userMarker.isInitialized){
//            마커가 존재할 시, addMarker말고 움직임으로 표현하기.
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

    fun addDiaryMarker(latlng: LatLng, id:Int){
        Log.e("그냥마커", latlng.toString())
        googleMap.addMarker(MarkerOptions()
            .position(latlng)
            .zIndex(1.0f)
            .title(id.toString()))!!
        Log.e("마커 생성", "id는 $id")
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