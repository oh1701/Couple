package com.project.myapplication.data.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity
data class RoomDiaryEntity (
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "imageUri") val imageUri: String,
    @ColumnInfo(name = "title") val title:String,
    @ColumnInfo(name = "date") val date:Long,
    @ColumnInfo(name = "longitude") val longitude:Long,
    @ColumnInfo(name = "latitude") val latitude:Long
    )
