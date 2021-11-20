package com.project.myapplication.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(private val repository: MainRepository):BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable

    private val _onClickView = MutableLiveData<Int>()
    val onClickView:LiveData<Int> = _onClickView
    private val _settingUpdate = MutableLiveData<Boolean>()
    val settingUpdate:LiveData<Boolean> = _settingUpdate

    fun coupleImageClick(number: Int){
        _onClickView.value = number
    }

    fun settingUpdate(){
        _settingUpdate.value = true
    }
}