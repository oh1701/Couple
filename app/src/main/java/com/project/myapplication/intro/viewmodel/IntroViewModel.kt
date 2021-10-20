package com.project.myapplication.intro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class IntroViewModel():BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable
}