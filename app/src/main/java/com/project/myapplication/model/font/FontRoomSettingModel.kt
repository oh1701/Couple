package com.project.myapplication.model.font

import com.project.myapplication.utils.FontToHtml

data class FontRoomSettingModel (
    val letterSpacing:Float?,
    val lineSpacing:Float?,
    val fontTypedSizeValue:Float?,
    val fontToHtml: String?
)