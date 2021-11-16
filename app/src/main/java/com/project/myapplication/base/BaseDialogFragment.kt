package com.project.myapplication.base

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.project.myapplication.utils.CheckSelfPermission
import com.project.myapplication.utils.SettingIntent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseDialogFragment<T: ViewDataBinding, V:BaseViewModel?>: DialogFragment(), CheckSelfPermission {
    abstract val layoutResourceId:Int
    abstract val thisViewModel:V

    private var _binding:T? = null
    private var toast: Toast? = null
    protected lateinit var metrics:DisplayMetrics
    protected val binding get() = _binding!! // Fragment에서 뷰바인딩 사용시 View보다 오래 남아있을 수 있는 문제가 있어, 이렇게 사용해야함.
    protected val compositeDisposable = CompositeDisposable()
    protected lateinit var commonIntent: SettingIntent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, layoutResourceId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        log("DialogFragment:${this::class.simpleName}", "onViewCreated")
        binding.lifecycleOwner = viewLifecycleOwner // Fragment에서는 this 말고 뷰 라이프사이클 오너로 설정해주기.
        metrics = resources.displayMetrics
        commonIntent = SettingIntent(requireActivity())
        initView()
        initObserve()
    }

    abstract fun initView()
    abstract fun initObserve()

    protected fun toast(msg:String){ // 토스트 메세지 설정.
        if(toast != null){
            toast!!.cancel()
        }

        toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
        toast!!.show()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    protected fun log(title:String, content:String){ // 로그 설정
        Log.e("APP_LOG::$title", content)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        compositeDisposable.dispose()
        _binding = null
        log("Fragment:${this::class.simpleName}", "onDestroyView")
    }
}