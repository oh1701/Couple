package com.project.myapplication.ui

import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(private val repository: MainRepository):BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable
}