package com.project.myapplication.ui.intro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.utils.Event
import com.project.myapplication.ui.intro.repository.FirstAccessSettingRepository
import java.util.*

class FirstAccessSettingViewModel(private val repository: FirstAccessSettingRepository):BaseViewModel() {
    private val _saveCoupleDateButton = MutableLiveData<Event<Boolean>>()
    val saveCoupleDateButton:LiveData<Event<Boolean>> = _saveCoupleDateButton
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
        repository.saveSharedCoupleDate(saveCoupleDate.value!!)
        _saveCoupleDateButton.value = Event(true)
    }
}