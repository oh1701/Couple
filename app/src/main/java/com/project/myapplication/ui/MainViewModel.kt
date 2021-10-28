package com.project.myapplication.ui

import com.project.myapplication.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(private val repository: MainRepository):BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable
}