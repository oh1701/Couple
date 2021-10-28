package com.project.myapplication.ui.intro.viewmodel

import com.project.myapplication.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class IntroViewModel():BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable
}