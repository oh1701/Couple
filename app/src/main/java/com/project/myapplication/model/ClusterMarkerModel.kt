package com.project.myapplication.model

/** GoogleMap Marker Cluster Data Class*/

data class ClusterMarkerModel(
    val imageUri: List<String>?,
    val title:String,
    val content:String?,
    val date:String,
    val tag:String?
)