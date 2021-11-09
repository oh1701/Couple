package com.project.myapplication.ui.intro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.base.BaseViewModel
import java.util.*

class FirstAccessSettingViewModel:BaseViewModel() {
    private val _saveCoupleDateButton = MutableLiveData<Boolean>()
    val saveCoupleDateButton:LiveData<Boolean> = _saveCoupleDateButton
    private val _saveCoupleDate = MutableLiveData<List<Int?>>()
    val saveCoupleDate:LiveData<List<Int?>> = _saveCoupleDate

    val maxDate = MutableLiveData<Long>()
    val coupleDateYear = MutableLiveData<Int>()
    val coupleDateMonth = MutableLiveData<Int>()
    val coupleDateDay = MutableLiveData<Int>()


    init {
        val today = GregorianCalendar()

        coupleDateYear.value = today.get(Calendar.YEAR)
        coupleDateMonth.value = today.get(Calendar.MONTH)
        coupleDateDay.value = today.get(Calendar.DATE)
        maxDate.value = Calendar.getInstance().timeInMillis
    }

    fun saveButton(){
        coupleDateMonth.value = coupleDateMonth.value?.plus(1)
        _saveCoupleDate.value = listOf(coupleDateYear.value, coupleDateMonth.value, coupleDateDay.value)
        _saveCoupleDateButton.value = true
    }
}