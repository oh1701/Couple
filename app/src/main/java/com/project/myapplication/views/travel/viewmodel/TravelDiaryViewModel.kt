package com.project.myapplication.views.travel.viewmodel

import com.project.myapplication.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class TravelDiaryViewModel:BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable

    override fun onCleared() {
        super.onCleared()
    }
}