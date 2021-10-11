package com.project.myapplication.intro.viewmodel

import android.util.Log
import com.project.myapplication.base.BaseRepository
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.intro.repository.IntroRepository
import io.reactivex.disposables.CompositeDisposable

class IntroViewModel:BaseViewModel<IntroRepository>() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable
    override var repository: IntroRepository
        get() = super.repository
        set(value) {}

    fun aa(){
        val introTimeCheck = repository.introTime().subscribe {
            Log.e("확인", "확인")
        }

        compositeDisposable.add(introTimeCheck)
    }
}