package com.project.myapplication.base

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<T: ViewDataBinding, V:BaseViewModel<BaseRepository>>:AppCompatActivity() {
    abstract val layoutResourceId:Int
    abstract val thisviewModel:V
    lateinit var binding:T

    private var toast:Toast? = null
    protected val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutResourceId)
        binding.lifecycleOwner = this

        initView()
        initObserve()
    }

    abstract fun initView()
    abstract fun initObserve()

    protected fun toast(msg:String){ // 토스트 메세지 설정.
        if(toast != null){
            toast!!.cancel()
        }

        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        toast!!.show()
    }

    protected fun log(title:String, content:String){
        Log.d(title, content)
    }

    override fun onDestroy() { // compositeDisposable 해제
        super.onDestroy()

        log("OnDestroy", "OnDestroy")
        compositeDisposable.dispose()
    }
}