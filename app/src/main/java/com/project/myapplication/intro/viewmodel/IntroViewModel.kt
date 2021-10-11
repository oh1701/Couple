package com.project.myapplication.intro.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.base.BaseRepository
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.intro.repository.IntroRepository
import io.reactivex.disposables.CompositeDisposable

class IntroViewModel(private val repository: IntroRepository):BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable

    private val _moveActivity = MutableLiveData<Boolean>()
    val moveActivity: LiveData<Boolean> = _moveActivity

    fun timeCheck(){
        val introTimeCheck = repository.introTime().subscribe {
            _moveActivity.value = true
        }

        compositeDisposable.add(introTimeCheck)
    }
}