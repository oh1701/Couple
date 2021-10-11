package com.project.myapplication.intro.repository

import com.project.myapplication.base.BaseRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class IntroRepository:BaseRepository() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable

    fun introTime(): Observable<Long>{
        val introTime = Observable.timer(1500L, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        return introTime
    }

}