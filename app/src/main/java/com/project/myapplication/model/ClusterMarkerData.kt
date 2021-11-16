package com.project.myapplication.model

import androidx.room.ColumnInfo

/** GoogleMap Marker Cluster Data Class*/

data class ClusterMarkerData(
    val imageUri: String?,
    val title:String,
    val content:String?,
    val date:String
)