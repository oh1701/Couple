package com.project.myapplication.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.project.myapplication.common.CheckSelfPermission
import com.project.myapplication.common.MoveFragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<T: ViewDataBinding, V:BaseViewModel?>: Fragment(), CheckSelfPermission {
    abstract val layoutResourceId:Int
    abstract val thisViewModel:V

    private var _binding:T? = null
    private var toast:Toast? = null

    protected val binding get() = _binding!! // Fragment에서 뷰바인딩 사용시 View보다 오래 남아있을 수 있는 문제가 있어, 이렇게 사용해야함.
    protected val compositeDisposable = CompositeDisposable()
    protected val moveFragment = MoveFragment()
    protected lateinit var supportFragmentManager: FragmentManager
    private lateinit var backPressedCallback: OnBackPressedCallback

    override fun onAttach(context: Context) { // startFragment가 아닌 곳에서 뒤로가기 누를 시, Fragment는 start로 되돌아간다.
        super.onAttach(context)

        supportFragmentManager = requireActivity().supportFragmentManager
    }

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

        log("Fragment:${this::class.simpleName}", "onViewCreated")
        binding.lifecycleOwner = viewLifecycleOwner // Fragment에서는 this 말고 뷰 라이프사이클 오너로 설정해주기.
        initView()
        initObserve()
        sharedObserve()
    }

    abstract fun initView()
    abstract fun initObserve()
    open fun sharedObserve(){}

    protected fun toast(msg:String){ // 토스트 메세지 설정.
        if(toast != null){
            toast!!.cancel()
        }

        toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
        toast!!.show()
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