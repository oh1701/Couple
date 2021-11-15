package com.project.myapplication.ui.travel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.data.room.entity.RoomDiaryEntity
import com.project.myapplication.ui.travel.repository.TravelMapRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TravelMapViewModel(private val repository: TravelMapRepository):BaseViewModel() {
    private val _googleMapDiaryMarker = MutableLiveData<List<RoomDiaryEntity>>()
    val googleMapDiaryMarker:LiveData<List<RoomDiaryEntity>> = _googleMapDiaryMarker

    fun getAllDiary(){ // 저장된 다이어리들 가져오기.
        compositeDisposable.add(repository.getAllDiary()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _googleMapDiaryMarker.value = it
            }
            .doOnError {
                throwableMessage(it)
            }
            .subscribe())
    }

}