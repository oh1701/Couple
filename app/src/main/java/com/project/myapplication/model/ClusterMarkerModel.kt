package com.project.myapplication.model

/** GoogleMap Marker Cluster Data Class*/

data class ClusterMarkerModel(
    val imageUri: List<String>?,
    val title:String,
    val content:String?,
    val createDay:String,
    val coupleDay:String,
    val tag:String?
)