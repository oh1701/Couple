package com.project.myapplication.utils.customobserver

import androidx.lifecycle.Observer

// 한번만 처리되어야 하는 이벤트 ( 뷰 파괴되었을때 방지용)

open class Event<out T>(private val content: T) {
    private var hasBeenHandled = false
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) { // 이벤트가 이미 처리 되었다면
            null // null을 반환하고,
        } else { // 그렇지 않다면
            hasBeenHandled = true // 이벤트가 처리되었다고 표시한 후에
            content // 값을 반환합니다.
        }
    }

    /**
     * 이벤트의 처리 여부에 상관 없이 값을 반환합니다.
     */
    fun peekContent(): T = content
}

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let {
                value -> onEventUnhandledContent(value)
        }
    }
}