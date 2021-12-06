package com.project.myapplication.googlemap

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.project.myapplication.data.room.entity.RoomDiaryEntity
import com.project.myapplication.model.ClusterMarkerModel
import com.project.myapplication.ui.travel.view.TravelMapFragment
import com.project.myapplication.ui.travel.viewmodel.TravelMapViewModel

class ClusterSetting {
    fun <myViewModel>setCluster(context: Context, cluster: ClusterManager<MarkerClusterItem>, map: GoogleMap, viewModel: myViewModel){
        val render = MarkerClusterRenderer(
            context = context,
            map = map,
            clusterManager = cluster
        )
        cluster.renderer = render
        map.setOnCameraIdleListener(cluster) // 지도 화면의 움직임 감시
        map.setOnMarkerClickListener(cluster) // 마커 클릭시 실행하는 함수

        cluster.setOnClusterItemClickListener { markerItem ->
            when(viewModel){
                is TravelMapViewModel -> {
                    viewModel.markerClickListener(arrayListOf(markerItem.title ?: "create"))
                }
            }
            return@setOnClusterItemClickListener true
        }

        cluster.setOnClusterClickListener { markerClusterItem ->
            when(viewModel){
                is TravelMapViewModel -> {
                    val list = arrayListOf<String>()
                    markerClusterItem.items.forEach {
                        list.add(it.title ?: "create")
                    }
                    viewModel.markerClickListener(list)
                }
            }
            return@setOnClusterClickListener true
        }
    }

    fun clusterAddItemList(cluster: ClusterManager<MarkerClusterItem>, diary:List<RoomDiaryEntity>){ // Generic으로 raw type 확인 못함(is List<value>) 불가.
        diary.map { diary ->
            cluster.addItem(
                MarkerClusterItem(
                    LatLng(diary.latitude.toDouble(), diary.longitude.toDouble()),
                    diary.id.toString(),
                    null,
                    ClusterMarkerModel(
                        diary.imageUri,
                        diary.title,
                        diary.content,
                        diary.createDay,
                        diary.coupleDay,
                        null
                    )
                )
            )
        }
        cluster.cluster()
    }

    fun clusterAddItem(cluster: ClusterManager<MarkerClusterItem>, diary:RoomDiaryEntity){
        cluster.addItem(
            MarkerClusterItem(
                LatLng(diary.latitude.toDouble(), diary.longitude.toDouble()),
                diary.id.toString(),
                null,
                ClusterMarkerModel(
                    diary.imageUri,
                    diary.title,
                    diary.content,
                    diary.createDay,
                    diary.coupleDay,
                    null
                )
            )
        )
        cluster.cluster()
    }

}