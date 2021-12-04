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

// 왜인지는 모르겠으나, targetView를 전역으로 설정하면 뷰가 안입혀진다.

class MarkerClusterRenderer(val context: Context, private val map: GoogleMap, private val clusterManager: ClusterManager<MarkerClusterItem>):
    DefaultClusterRenderer<MarkerClusterItem>(context, map, clusterManager)  {
    private val glideUtils = GlideUtils(context)

    private fun getObservableMarker(cluster: Cluster<MarkerClusterItem>):String?{
        val reverseCluster = cluster.items.toMutableList().reversed()
        var uri = ""

        var int = 1
        reverseCluster.run {
            this.forEach {
                if(!it.diaryData().imageUri.isNullOrEmpty()) {
                    uri = it.diaryData().imageUri!![0] // 마커 reverse 후 0번째것을 마커 대표 이미지로 설정. (최근 콘텐츠 이미지)
                    int++
                    return@run
                }
            }
        }

        return uri
    }

    override fun onBeforeClusterItemRendered(item: MarkerClusterItem, markerOptions: MarkerOptions) {
        itemGlide(item, markerOptions)
    }

    override fun onBeforeClusterRendered(cluster: Cluster<MarkerClusterItem>, markerOptions: MarkerOptions) {
        clusterGlide(cluster, markerOptions)
    }

    override fun onClusterItemRendered(clusterItem: MarkerClusterItem, marker: Marker) {
        itemGlide(clusterItem, marker)
    }

    override fun onClusterRendered(cluster: Cluster<MarkerClusterItem>, marker: Marker) {
        clusterGlide(cluster, marker)
    }

    override fun onClusterItemUpdated(item: MarkerClusterItem, marker: Marker) {
        itemGlide(item, marker)
    }

    override fun onClusterUpdated(cluster: Cluster<MarkerClusterItem>, marker: Marker) {
        clusterGlide(cluster, marker)
    }

    override fun shouldRenderAsCluster(cluster: Cluster<MarkerClusterItem>): Boolean {
        return cluster.size > 1 // 클러스터 될 콘텐츠가 1개 초과시
    }

    private fun setTextView(v:View, cluster:Cluster<MarkerClusterItem>){
        v.findViewById<TextView>(R.id.cluster_count).apply{
            text = if(cluster.size > 9){
                "10+"
            } else{
                cluster.size.toString()
            }
        }
    }

    private fun <mapMarker>itemGlide(cluster: MarkerClusterItem, marker:mapMarker){
        val targetview: View = LayoutInflater.from(context).inflate(R.layout.google_mapmarker, null)
        val imageview = targetview.findViewById<ImageView>(R.id.marker_image)
        val image = cluster.diaryData().imageUri

        if(image?.isNotEmpty() == true) {
            glideUtils.glideListener(
                targetview,
                image[0],
                imageview,
                marker,
                "cluster"
            )
        }
        else{
            glideUtils.glideListener(
                targetview,
                null,
                imageview,
                marker,
                "cluster"
            )
        }
    }

    private fun <mapMarker>clusterGlide(cluster: Cluster<MarkerClusterItem>, marker:mapMarker){
        val targetview: View = LayoutInflater.from(context).inflate(R.layout.google_mapmarker, null)
        val imageview = targetview.findViewById<ImageView>(R.id.marker_image)
        setTextView(targetview, cluster)

        glideUtils.glideListener(targetview, getObservableMarker(cluster), imageview, marker, "cluster")
    }
}