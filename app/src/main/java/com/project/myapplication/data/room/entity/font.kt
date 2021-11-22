package com.project.myapplication.data.room.entity

import android.text.Spannable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Font(
    @PrimaryKey(autoGenerate = false) val id:Int, //1, 2 로 나누기
    @ColumnInfo(name = "fontColor") val fontColor:Int,
    @ColumnInfo(name = "selectionStart") val selectionStart:Int,
    @ColumnInfo(name = "selectionEnd") val selectionEnd:Int
)
