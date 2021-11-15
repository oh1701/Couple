package com.project.myapplication.common

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

class CommonIntent(private val activity: Activity) {
    fun dialogIntentSetting() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse(
                "package:" + activity.packageName
            )
        ) //어플 정보를 가진 설정창으로 이동.
        activity.startActivity(intent)
    }
}