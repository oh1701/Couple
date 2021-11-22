package com.project.myapplication.utils

import android.app.DatePickerDialog
import android.content.Context
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.ui.start.viewmodel.SetCoupleViewModel
import java.util.*

/** Create By Gyu Seong Oh. 2021 / 11 */

class Datepicker(private val context: Context) { // di 사용못함. applicationContext 사용하면 안됨
    fun <T>datePicker(viewModel: T) {
        when(viewModel){
            is SetCoupleViewModel -> {
                val today = GregorianCalendar()
                val todayYear = today.get(Calendar.YEAR)
                val todayMonth = today.get(Calendar.MONTH)
                val todayDay = today.get(Calendar.DATE)

                DatePickerDialog(context, { _, year, month, day ->
                    viewModel.setBirthDay(year, month + 1, day)
                }, todayYear, todayMonth, todayDay)
                    .apply {
                        this.datePicker.maxDate = Calendar.getInstance().timeInMillis
                    }
                    .show()
            }
        }
    }
}