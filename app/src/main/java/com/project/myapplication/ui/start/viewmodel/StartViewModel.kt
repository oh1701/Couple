package com.project.myapplication.ui.start.viewmodel

import android.text.SpannableStringBuilder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.ui.start.repository.StartRepository
import io.reactivex.disposables.CompositeDisposable

class StartViewModel(private val repository: StartRepository):BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable

    private val _myDatetime = MutableLiveData<SpannableStringBuilder>()
    val myDatetime: LiveData<SpannableStringBuilder> = _myDatetime

    fun getmyDatetime(){
        _myDatetime.value = repository.getDateTime()
    }
}