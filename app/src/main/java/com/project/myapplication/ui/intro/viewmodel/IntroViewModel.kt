package com.project.myapplication.ui.intro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.common.Event
import com.project.myapplication.ui.MainActivity
import com.project.myapplication.ui.intro.repository.IntroRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class IntroViewModel(private val repository: IntroRepository):BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable

    private val _checkCoupleDateSetting = MutableLiveData<Event<Boolean>>()
    val checkCoupleDateSetting:LiveData<Event<Boolean>> = _checkCoupleDateSetting

    fun timer(){
        val timer = Observable.timer(3000L, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(){
                getCoupleDateSetting()
            }

        compositeDisposable.add(timer)
    }

    private fun getCoupleDateSetting(){
        _checkCoupleDateSetting.value = Event(repository.checkCoupleDateSetting())
    }
}