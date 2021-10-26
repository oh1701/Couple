package com.project.myapplication.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.project.myapplication.views.intro.activity.IntroActivity
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<T: ViewDataBinding, V:BaseViewModel?>:AppCompatActivity() {
    abstract val layoutResourceId:Int
    abstract val thisViewModel:V
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

    protected fun <T> moveActivity(moveName: Class<T>){ // Intent 설정
        val intent = Intent(this@BaseActivity, moveName)

        if(this.javaClass == IntroActivity::class.java){ // 만약 현재 클래스가 IntroActivity 면 액티비티 플래그 제거 후 이동.
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        startActivity(intent)
    }

    protected fun toast(msg:String){ // 토스트 메세지 설정.
        if(toast != null){
            toast!!.cancel()
        }

        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        toast!!.show()
    }

    protected fun log(content:String){ // 로그 설정
        Log.e("APP_LOG::${this::class.simpleName}", content)
    }

    override fun onDestroy() { // compositeDisposable 해제
        super.onDestroy()

        log("_Activity:${this::class.simpleName}::onDestroy")
        compositeDisposable.dispose()
    }
}