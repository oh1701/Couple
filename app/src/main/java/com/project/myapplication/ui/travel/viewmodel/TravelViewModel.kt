package com.project.myapplication.ui.travel.viewmodel

import com.project.myapplication.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class TravelViewModel:BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable

    override fun onCleared() {
        super.onCleared()
    }
}