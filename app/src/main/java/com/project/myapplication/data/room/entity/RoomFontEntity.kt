package com.project.myapplication.data.room.entity

import android.content.res.ColorStateList
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.RawValue

@Entity
data class RoomFontEntity(
    @PrimaryKey(autoGenerate = false) val id:Int, //1, 2 로 나누기
    @ColumnInfo(name = "textTypeFace") val textTypeFace:String?,
    @ColumnInfo(name = "colorInt") val colorInt:Int,
    @ColumnInfo(name = "letterSpacingContent") val letterSpacing:Float?,
    @ColumnInfo(name = "lineSpacingContent") val lineSpacing:Float?,
    @ColumnInfo(name = "fontTypedSizeValue") val fontTypedSizeValue:Float?,
    @ColumnInfo(name = "colorList") val colorList: List<Int>
)