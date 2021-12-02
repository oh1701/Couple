package com.project.myapplication.utils.customobserver

import androidx.databinding.adapters.NumberPickerBindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


open class ListMutableLiveData <T>:MutableLiveData<ArrayList<T>>() {
    open fun listLiveData() { // 초기 설정. value를 가져온 ArrayList 형식으로 지정
        value = ArrayList() //NPE 방지용 (NullPointerException). null확인 시 오류남.
    }

    open fun add(item: T) {
        val items: ArrayList<T> = value!! // items 는 Value
        items.add(item) // items에 item 추가
        value = items // value는 items
    }

    open fun addAll(list: List<T>?) {
        val items: ArrayList<T> = value!!
        items.addAll(list!!)
        value = items
    }

    open fun clear(notify: Boolean) {
        val items: ArrayList<T> = value!!
        items.clear()
        if (notify) {
            value = items
        }
    }

    open fun remove(item: T) {
        val items: ArrayList<T> = value!!
        items.remove(item)
        value = items
    }

    open fun notifyChange() {
        val items: ArrayList<T> = value!!
        value = items
    }
}
