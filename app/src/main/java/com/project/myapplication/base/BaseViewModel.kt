package com.project.myapplication.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

open class BaseViewModel<R:BaseRepository>: ViewModel(), KoinComponent{
    open val compositeDisposable = CompositeDisposable()
    open lateinit var repository: R

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}