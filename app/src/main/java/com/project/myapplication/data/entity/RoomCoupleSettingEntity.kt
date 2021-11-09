package com.project.myapplication.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomCoupleSettingEntity(
    @PrimaryKey(autoGenerate = false) val id:Int, //1, 2 로 나누기
    @ColumnInfo(name = "imageUri") val uri:String?,
    @ColumnInfo(name = "userName") val name:String?,
    @ColumnInfo(name = "birthYear") val birthYear:String?,
    @ColumnInfo(name = "birthMonth") val birthMonth:String?,
    @ColumnInfo(name = "birthDay") val birthDay:String?
)
