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
import com.project.myapplication.data.entity.RoomEntityImage
import com.project.myapplication.ui.travel.repository.TravelDiaryRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

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

    fun getImage(){
        compositeDisposable.add(repository.selectDB("14")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { _diaryImageUri.value = Uri.parse(it.imageUri) }
            .doOnError { Log.e("실패하였음", "실패") }
            .subscribe())
    }

    fun setDiaryImage(){
        _imageClick.value = true
    }

    fun getUri(uri: Uri){
        _diaryImageUri.value = uri

        compositeDisposable.add(repository.insertDB(RoomEntityImage(0, uri.toString(), "14", 1414))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { Log.e("성공하였음", "성공") }
            .doOnError {  Log.e("실패하였음", "실패")  }
            .subscribe())
    }
}