package com.project.myapplication.ui.travel.viewmodel

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.ui.travel.repository.TravelDiaryRepository
import io.reactivex.disposables.CompositeDisposable

class TravelDiaryViewModel(private val repository: TravelDiaryRepository):BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable
    private val _diaryImageUri = MutableLiveData<Uri>(null)
    val diaryImageUri : LiveData<Uri> = _diaryImageUri
    private val _imageClick = MutableLiveData<Boolean>()
    val imageClick:LiveData<Boolean> = _imageClick

    override fun onCleared() {
        super.onCleared()
    }

    fun setDiaryImage(){
        _imageClick.value = true
    }

    fun getUri(uri: Uri){
        _diaryImageUri.value = uri
        Log.e("위치는", "$uri")
        repository.insertDB()
    }
}