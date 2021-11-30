package com.project.myapplication.model.font

import android.content.res.ColorStateList
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FontBindSettingModel(
    val letterSpacing:Float?,
    val lineSpacing:Float?,
    val fontTypedSizeValue:Float?,
    val colorHex:ColorStateList?
): Parcelable

