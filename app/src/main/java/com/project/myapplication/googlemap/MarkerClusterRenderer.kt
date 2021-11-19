package com.project.myapplication.googlemap

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.target.ViewTarget
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.project.myapplication.R
import com.project.myapplication.utils.GlideUtils
import io.reactivex.Observable

class MarkerClusterRenderer(val context: Context, private val map: GoogleMap, private val clusterManager: ClusterManager<MarkerClusterItem>):
    DefaultClusterRenderer<MarkerClusterItem>(context, map, clusterManager)  {
    private val glideUtils = GlideUtils(context)

    private fun getObservableMarker(cluster: Cluster<MarkerClusterItem>):String?{
        return cluster.items.toMutableList()[cluster.size - 1].diaryData().imageUri
    }

    override fun onBeforeClusterItemRendered(item: MarkerClusterItem, markerOptions: MarkerOptions) {
         val targetview: View = LayoutInflater.from(context).inflate(R.layout.google_mapmarker, null)
         val imageview = targetview.findViewById<ImageView>(R.id.marker_image)
        glideUtils.glideListener(targetview, item.diaryData().imageUri, imageview, markerOptions, "cluster")
    }

    override fun onBeforeClusterRendered(cluster: Cluster<MarkerClusterItem>, markerOptions: MarkerOptions) {
         val targetview: View = LayoutInflater.from(context).inflate(R.layout.google_mapmarker, null)
        targetview.findViewById<TextView>(R.id.cluster_count).apply{
            text = if(cluster.size > 9){
                "10+"
            } else{
                cluster.size.toString()
            }
        }
         val imageview = targetview.findViewById<ImageView>(R.id.marker_image)
        glideUtils.glideListener(targetview, getObservableMarker(cluster), imageview, markerOptions, "cluster")
    }

    override fun onClusterItemRendered(clusterItem: MarkerClusterItem, marker: Marker) {
         val targetview: View = LayoutInflater.from(context).inflate(R.layout.google_mapmarker, null)
         val imageview = targetview.findViewById<ImageView>(R.id.marker_image)
        glideUtils.glideListener(targetview, clusterItem.diaryData().imageUri, imageview, marker, "cluster")
    }

    override fun onClusterRendered(cluster: Cluster<MarkerClusterItem>, marker: Marker) {
         val targetview: View = LayoutInflater.from(context).inflate(R.layout.google_mapmarker, null)
        targetview.findViewById<TextView>(R.id.cluster_count).apply{
            text = if(cluster.size > 9){
                "10+"
            } else{
                cluster.size.toString()
            }
        }
         val imageview = targetview.findViewById<ImageView>(R.id.marker_image)
        glideUtils.glideListener(targetview, getObservableMarker(cluster), imageview, marker, "cluster")
    }

    override fun onClusterItemUpdated(item: MarkerClusterItem, marker: Marker) {
         val targetview: View = LayoutInflater.from(context).inflate(R.layout.google_mapmarker, null)
         val imageview = targetview.findViewById<ImageView>(R.id.marker_image)
        glideUtils.glideListener(targetview, item.diaryData().imageUri, imageview, marker, "cluster")
    }

    override fun onClusterUpdated(cluster: Cluster<MarkerClusterItem>, marker: Marker) {
         val targetview: View = LayoutInflater.from(context).inflate(R.layout.google_mapmarker, null)
        targetview.findViewById<TextView>(R.id.cluster_count).apply{
            text = if(cluster.size > 9){
                "10+"
            } else{
                cluster.size.toString()
            }
        }
         val imageview = targetview.findViewById<ImageView>(R.id.marker_image)

        glideUtils.glideListener(targetview, getObservableMarker(cluster), imageview, marker, "cluster")
    }

    private fun createDrawableFromView(v: View): Bitmap { // 뷰를 마커로 출력해주기 위한 함수
        v.measure(v.width, v.height)
        val b = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        v.layout(0, 0, v.measuredWidth, v.measuredHeight)
        v.draw(c)
        return b
    }

    override fun shouldRenderAsCluster(cluster: Cluster<MarkerClusterItem>): Boolean {
        return cluster.size > 1 // 클러스터 될 콘텐츠가 1개 초과시
    }
}

//Glide.with(targetview)
//.load(imageview)
//.circleCrop()
//.listener(object : RequestListener<Drawable> {
//    override fun onLoadFailed(
//        e: GlideException?,
//        model: Any?,
//        target: Target<Drawable>?,
//        isFirstResource: Boolean
//    ): Boolean {
//        return false
//    }
//
//    override fun onResourceReady(
//        resource: Drawable?,
//        model: Any?,
//        target: Target<Drawable>?,
//        dataSource: DataSource?,
//        isFirstResource: Boolean
//    ): Boolean {
//        BitmapDescriptorFactory.fromBitmap(createDrawableFromView(targetview))
//        return false
//    }
//})
//.into(imageview)