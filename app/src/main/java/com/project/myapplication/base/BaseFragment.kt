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
import com.project.myapplication.common.MoveFragment
import com.project.myapplication.databinding.FragmentStartBinding
import com.project.myapplication.start.StartFragment
import io.reactivex.disposables.CompositeDisposable
import kotlin.reflect.jvm.jvmName

abstract class BaseFragment<T: ViewDataBinding, V:BaseViewModel>: Fragment() {
    abstract val layoutResourceId:Int
    abstract val thisViewModel:V
    private var _binding:T? = null
    protected val binding get() = _binding!! // Fragment에서 뷰바인딩 사용시 View보다 오래 남아있을 수 있는 문제가 있어, 이렇게 사용해야함.

    private var toast:Toast? = null
    private lateinit var backPressedCallback: OnBackPressedCallback
    protected val compositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context) { // startFragment가 아닌 곳에서 뒤로가기 누를 시, Fragment는 start로 되돌아간다.
        super.onAttach(context)

        backPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(this::class.simpleName == "StartFragment"){
                    log("정상", "정상")
                }
                else{
                    MoveFragment().moveFragment(requireActivity().supportFragmentManager, StartFragment())
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
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

        binding.lifecycleOwner = viewLifecycleOwner // Fragment에서는 this 말고 뷰 라이프사이클 오너로 설정해주기.
        initView()
        initObserve()
    }

    abstract fun initView()
    abstract fun initObserve()
    protected fun toast(msg:String){ // 토스트 메세지 설정.
        if(toast != null){
            toast!!.cancel()
        }

        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        toast!!.show()
    }

    protected fun log(title:String, content:String){ // 로그 설정
        Log.e("MYAPP_LOG$title", content)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        compositeDisposable.dispose()
        _binding = null
        log("Fragment:${this::class.simpleName}", "onDestroyView")
    }

}