package com.project.myapplication.googlemap

import android.content.Context
import android.os.SystemClock
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.project.myapplication.data.room.entity.RoomDiaryEntity
import com.project.myapplication.model.ClusterMarkerModel
import com.project.myapplication.ui.travel.viewmodel.TravelMapViewModel

class ClusterSetting {
    private var lastClickTime = 0L

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
            if((SystemClock.elapsedRealtime() - lastClickTime) > 1000) { // 중복 클릭 방지
                when (viewModel) {
                    is TravelMapViewModel -> viewModel.markerClickListener(
                        arrayListOf(
                            markerItem.title ?: "create"
                        )
                    )
                }
                lastClickTime = SystemClock.elapsedRealtime()
            }
            return@setOnClusterItemClickListener true

        }

        cluster.setOnClusterClickListener { markerClusterItem ->
            if((SystemClock.elapsedRealtime() - lastClickTime) > 1000) { // 중복 클릭 방지
                when (viewModel) {
                    is TravelMapViewModel -> {
                        val list = arrayListOf<String>()
                        markerClusterItem.items.forEach {
                            list.add(it.title ?: "create")
                        }
                        viewModel.markerClickListener(list)
                    }
                }
                lastClickTime = SystemClock.elapsedRealtime()
            }
            return@setOnClusterClickListener true
        }
    }

    fun clusterItemList(cluster: ClusterManager<MarkerClusterItem>, diary:List<RoomDiaryEntity>){ // Generic으로 raw type 확인 못함(is List<value>) 불가.
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
        Log.e("현재 사이즈 리스트", cluster.algorithm.items.toString())
        cluster.cluster()
    }

    fun clusterItem(cluster: ClusterManager<MarkerClusterItem>, diary:RoomDiaryEntity, crud: String){
        when(crud){
            "ADD" -> {
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
            "REMOVE" -> {
                cluster.removeItem(
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
        }
        Log.e("현재 사이즈", cluster.algorithm.items.toString())
        cluster.cluster()
    }
}

