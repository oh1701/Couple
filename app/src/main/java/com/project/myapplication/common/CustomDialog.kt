package com.project.myapplication.common

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.project.myapplication.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomDialog(private val context: Context) {
    fun warningDialog(call_check: String){
        val metrics = context.resources.displayMetrics
        val permissionView: View = LayoutInflater.from(context).inflate(R.layout.dialog_permission_intent, null)// 커스텀 다이얼로그 생성하기. 권한은 저장공간, 카메라

        val dialog = Dialog(context)
        dialog.setContentView(permissionView)

        val positiveBtn =
            permissionView.findViewById<Button>(R.id.warning_positive)
        val negativeBtn =
            permissionView.findViewById<Button>(R.id.warning_negative)
        val text = permissionView.findViewById<TextView>(R.id.warnig_text)

        when(call_check) {
            "permissionCheck" -> {
                positiveBtn.setOnClickListener { //설정버튼 누를시 이동
                    context.startActivity(
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse(
                                "package:" + context.packageName // uristring
                            )
                        )
                    ) //어플 정보를 가진 설정창으로 이동.
                    dialog.dismiss()
                }

                negativeBtn.setOnClickListener {
                    dialog.dismiss()
                }
            }

            "onBackPressed" -> {
                text.text = "현재 기록되어 있는 내용이 존재합니다. 저장하시겠습니까?"
                positiveBtn.text = "저장"
                negativeBtn.text = "삭제"

                positiveBtn.setOnClickListener {

                }

                negativeBtn.setOnClickListener {
                    dialog.dismiss()
                }
            }
        }

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = metrics.widthPixels * 8 / 10 //레이아웃 params 에 width, height 넣어주기.
        lp.height = metrics.heightPixels * 6 / 10
        dialog.show()
        dialog.window!!.attributes = lp // 다이얼로그 표출 넓이 넣어주기.
    }
}