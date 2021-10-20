package com.project.myapplication.travel

import android.text.SpannableStringBuilder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class FragementViewModel(private val repository:FragmentRepository):BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable

    private val _myDatetime = MutableLiveData<SpannableStringBuilder>()
    val myDatetime: LiveData<SpannableStringBuilder> = _myDatetime

    fun getmyDatetime(){
        _myDatetime.value = repository.getDateTime()
    }
}