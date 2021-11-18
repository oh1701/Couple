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
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.project.myapplication.R
import io.reactivex.Observable

class MarkerClusterRenderer(val context: Context, private val map: GoogleMap, private val clusterManager: ClusterManager<MarkerClusterItem>):
    DefaultClusterRenderer<MarkerClusterItem>(context, map, clusterManager)  {
    private val targetview: View = LayoutInflater.from(context).inflate(R.layout.google_mapmarker, null)

    private fun customMarkerView(): BitmapDescriptor {
        return BitmapDescriptorFactory.fromBitmap(createDrawableFromView(targetview))
    }

    private inline fun <reified T>getObservableMarker(cluster: T): Observable<ViewTarget<ImageView, Drawable>> {
        val imageview = targetview.findViewById<ImageView>(R.id.marker_image)
        var uri:String? = null
        if(cluster is Cluster<MarkerClusterItem>) {
            targetview.findViewById<TextView>(R.id.cluster_count).apply {
                text = if (cluster.size > 9) {
                    "10+"
                } else {
                    cluster.size.toString()
                }
            }

            uri = cluster.items.toMutableList()[cluster.size - 1]
        }
        else if(cluster is MarkerClusterItem){
            uri = cluster.diaryData().imageUri
        }
        return Observable.create { emitter ->
            emitter.onNext(
                Glide.with(targetview)
                    .load(uri)
                    .circleCrop()
                    .into(imageview)
            )
            emitter.onComplete()
        }
    }


    override fun onBeforeClusterItemRendered(item: MarkerClusterItem, markerOptions: MarkerOptions) {
        getObservableMarker(item)
        markerOptions.icon(customMarkerView())
    }

    override fun onBeforeClusterRendered(cluster: Cluster<MarkerClusterItem>, markerOptions: MarkerOptions) {
        getObservableMarker(cluster)
            .doOnComplete{
                markerOptions.icon(customMarkerView())
            }
            .subscribe()
    }

    override fun onClusterItemRendered(clusterItem: MarkerClusterItem, marker: Marker) {
        marker.setIcon(customMarkerView())
    }

    override fun onClusterRendered(cluster: Cluster<MarkerClusterItem>, marker: Marker) {
        getObservableMarker(cluster)
            .doOnComplete{
                marker.setIcon(customMarkerView())
            }
            .subscribe()
    }

    override fun getDescriptorForCluster(cluster: Cluster<MarkerClusterItem>): BitmapDescriptor {
        return customMarkerView()
    }

    override fun shouldRenderAsCluster(cluster: Cluster<MarkerClusterItem>): Boolean {
        return cluster.size > 1 // 클러스터 될 콘텐츠가 1개 초과시
    }

    private fun createDrawableFromView(v: View): Bitmap { // 뷰를 마커로 출력해주기 위한 함수
        v.measure(v.width, v.height)
        val b = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        v.layout(0, 0, v.measuredWidth, v.measuredHeight)
        v.draw(c)
        return b
    }
}