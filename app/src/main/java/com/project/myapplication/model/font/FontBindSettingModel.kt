package com.project.myapplication.model.font

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class FontBindSettingModel(
    val letterSpacing:Float?,
    val lineSpacing:Float?,
    val fontTypedSizeValue:Float?,
    val colorHex:ColorStateList?,
    val fontTypeFace: @RawValue Typeface
): Parcelable

