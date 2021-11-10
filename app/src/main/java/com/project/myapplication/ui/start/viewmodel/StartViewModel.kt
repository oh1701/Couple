package com.project.myapplication.ui.start.viewmodel

import android.net.Uri
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.ui.start.repository.StartRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class StartViewModel(private val repository: StartRepository):BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable

    private val _myDatetime = MutableLiveData<SpannableStringBuilder>()
    val myDatetime: LiveData<SpannableStringBuilder> = _myDatetime
    private val _coupleImage1 = MutableLiveData<String>()
    val coupleImage1:LiveData<String> = _coupleImage1
    private val _userName1 = MutableLiveData<String>()
    val userName1:LiveData<String> = _userName1
    private val _coupleImage2 = MutableLiveData<String>()
    val coupleImage2:LiveData<String> = _coupleImage2
    private val _userName2 = MutableLiveData<String>()
    val userName2:LiveData<String> = _userName2

    fun getmyDatetime(){
        _myDatetime.value = repository.getDateTime()
    }

    fun getCoupleSetting(){
        repository.getCoupleSetting()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { room ->
                Log.e("Room ::", "getCoupleSetting -> $room")
                room.map {
                    if(it.id == 1){
                        _coupleImage1.value = it.uri
                        _userName1.value = it.name
                    }
                    else{
                        _coupleImage2.value = it.uri
                        _userName2.value = it.name
                    }
                }
            }
            .doOnError {
                Log.e("Room ::", "getCoupleSetting failed")
            }
            .subscribe()
    }
}