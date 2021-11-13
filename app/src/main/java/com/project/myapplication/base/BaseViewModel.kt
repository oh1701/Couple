package com.project.myapplication.base

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.myapplication.common.Event
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

open class BaseViewModel: ViewModel(), KoinComponent{
    open val compositeDisposable = CompositeDisposable()
    private val _throwableMessage = MutableLiveData<Throwable>()
    open val throwableMessage:LiveData<Throwable> = _throwableMessage
    private val _toastLiveData = MutableLiveData<Event<String>>()
    open val toastLiveData:LiveData<Event<String>> = _toastLiveData

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    open fun throwableMessage(throwable: Throwable){
        _throwableMessage.value = throwable
    }

    open fun toast(str:String){
        _toastLiveData.value = Event(str)
    }
}