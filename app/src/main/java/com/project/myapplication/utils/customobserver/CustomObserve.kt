package com.project.myapplication.utils.customobserver

import androidx.lifecycle.Observer

// 처음 대입값은 observe 안하는 용도

open class CustomObserve<out T>(private val content:T, private val firstInitialization:Boolean) {
    fun getContentIfNotHandled(): T? {
        return if (firstInitialization) { // firstInitialization이 true 처음 대입하는 것이니 null 반환
            null // null을 반환하고,
        } else { // 그렇지 않다면
            content // 값을 반환합니다.
        }
    }
    fun peekContent(): T = content
}

class CustomObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<CustomObserve<T>> {
    override fun onChanged(event: CustomObserve<T>?) {
        event?.getContentIfNotHandled()?.let {
                value -> onEventUnhandledContent(value)
        }
    }
}