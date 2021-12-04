package com.project.myapplication.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomDiaryEntity (
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "imageUri") val imageUri: List<String>?,
    @ColumnInfo(name = "title") val title:String,
    @ColumnInfo(name = "content") val content:String,
    @ColumnInfo(name = "createDay") val createDay:String,
    @ColumnInfo(name = "coupleDay") val coupleDay:String,
    @ColumnInfo(name = "longitude") val longitude:Double,
    @ColumnInfo(name = "latitude") val latitude:Double
)
