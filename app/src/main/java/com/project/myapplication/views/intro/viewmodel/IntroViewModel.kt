package com.project.myapplication.views.intro.viewmodel

import com.project.myapplication.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class IntroViewModel():BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable
}