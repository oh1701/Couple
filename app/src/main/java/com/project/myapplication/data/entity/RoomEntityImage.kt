package com.project.myapplication.data.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomEntityImage (
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "imageUri") val imageUri: String,
    @ColumnInfo(name = "title") val title:String,
    @ColumnInfo(name = "date") val date:Long
    )
