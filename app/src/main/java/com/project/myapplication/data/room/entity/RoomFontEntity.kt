package com.project.myapplication.data.room.entity

import android.text.Spannable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomFontEntity(
    @PrimaryKey(autoGenerate = true) val id:Int, //1, 2 로 나누기
    @ColumnInfo(name = "titleFont") val titleFont:String,
    @ColumnInfo(name = "contentFont") val contentFont:String,
    @ColumnInfo(name = "letterSpacingContent") val letterSpacing:Float?,
    @ColumnInfo(name = "lineSpacingContent") val lineSpacing:Float?,
    @ColumnInfo(name = "fontTypedSizeValue") val fontTypedSizeValue:Float?
)
