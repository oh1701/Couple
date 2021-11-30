package com.project.myapplication.model

/** GoogleMap Marker Cluster Data Class*/

data class ClusterMarkerModel(
    val imageUri: String?,
    val title:String,
    val content:String?,
    val date:String,
    val tag:String?
)