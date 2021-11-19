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
    private val targetview: View = LayoutInflater.from(context).inflate(R.layout.google_mapmarker, null)
    private val imageview = targetview.findViewById<ImageView>(R.id.marker_image)
    private val glideUtils = GlideUtils(context)

    private fun getObservableMarker(cluster: Cluster<MarkerClusterItem>):String?{
        targetview.findViewById<TextView>(R.id.cluster_count).apply {
            text = if (cluster.size > 9) {
                "10+"
            } else {
                cluster.size.toString()
            }
        }
        return cluster.items.toMutableList()[cluster.size - 1].diaryData().imageUri
    }

    override fun onBeforeClusterItemRendered(item: MarkerClusterItem, markerOptions: MarkerOptions) {
        glideUtils.glideListener(targetview, item.diaryData().imageUri, imageview, markerOptions, "cluster")
    }

    override fun onBeforeClusterRendered(cluster: Cluster<MarkerClusterItem>, markerOptions: MarkerOptions) {
        glideUtils.glideListener(targetview, getObservableMarker(cluster), imageview, markerOptions, "cluster")
    }

    override fun onClusterItemRendered(clusterItem: MarkerClusterItem, marker: Marker) {
        glideUtils.glideListener(targetview, clusterItem.diaryData().imageUri, imageview, marker, "cluster")
    }

    override fun onClusterRendered(cluster: Cluster<MarkerClusterItem>, marker: Marker) {
        glideUtils.glideListener(targetview, getObservableMarker(cluster), imageview, marker, "cluster")
    }

    override fun onClusterItemUpdated(item: MarkerClusterItem, marker: Marker) {
        glideUtils.glideListener(targetview, item.diaryData().imageUri, imageview, marker, "cluster")
    }

    override fun onClusterUpdated(cluster: Cluster<MarkerClusterItem>, marker: Marker) {
        glideUtils.glideListener(targetview, getObservableMarker(cluster), imageview, marker, "cluster")
    }

    override fun shouldRenderAsCluster(cluster: Cluster<MarkerClusterItem>): Boolean {
        return cluster.size > 1 // 클러스터 될 콘텐츠가 1개 초과시
    }
}