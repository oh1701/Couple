package com.project.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.base.BaseRepository
import com.project.myapplication.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(private val repository: MainRepository):BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable

    private val _myDatetime = MutableLiveData<Int>()
    val myDatetime: LiveData<Int> = _myDatetime

    fun getmyDatetime(){
        _myDatetime.value = repository.getDateTime()
    }
}