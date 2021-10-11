package com.project.myapplication.base

import io.reactivex.disposables.CompositeDisposable

open class BaseRepository {
    open val compositeDisposable = CompositeDisposable()

    open fun cleardDisposable(){
        compositeDisposable.dispose()
    }
}