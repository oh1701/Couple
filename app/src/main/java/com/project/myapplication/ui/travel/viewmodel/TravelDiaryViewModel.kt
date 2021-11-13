package com.project.myapplication.ui.travel.viewmodel

import android.net.Uri
import android.opengl.Visibility
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.data.entity.RoomDiaryEntity
import com.project.myapplication.ui.travel.repository.TravelDiaryRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

/** insertDB는 작성완료했을때 RoomDiaryEntity를 파라미터값으로 받는다.*/

class TravelDiaryViewModel(private val repository: TravelDiaryRepository):BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable
    private val _diaryImageUri = MutableLiveData<String>(null)
    val diaryImageUri : LiveData<String> = _diaryImageUri
    
    // 초기 세팅
    private val _createDay = MutableLiveData<String>()
    val createDay:LiveData<String> = _createDay
    private val _createDiaryID = MutableLiveData<Int>() // 다이어리 생성 버튼 클릭 후 진입 시 생성되는 ID. List 사이즈 + 1
    val createDiaryID:LiveData<Int> = _createDiaryID
    
    // 다이어리 작성
    private val _createDiaryCoupleDay = MutableLiveData<String>()
    val createDiaryCoupleDay:LiveData<String> = _createDiaryCoupleDay
    private val _diaryViewVisibility = MutableLiveData<Int>()
    val diaryViewVisibility:LiveData<Int> = _diaryViewVisibility
    private val _diaryTrashBtnCheck = MutableLiveData<Boolean>()
    val diaryTrashBtnCheck:LiveData<Boolean> = _diaryTrashBtnCheck
    private val _diaryTouchBtnCheck = MutableLiveData<Boolean>()
    val diaryTouchBtnCheck:LiveData<Boolean> = _diaryTouchBtnCheck
    private val _diaryTagBtnCheck = MutableLiveData<Boolean>()
    val diaryTagBtnCheck:LiveData<Boolean> = _diaryTagBtnCheck

    // two-way binding
    val diaryTitle = MutableLiveData<String>()
    val diaryContent = MutableLiveData<String>()


    init{
        _diaryTrashBtnCheck.value = false
        _diaryTouchBtnCheck.value = false
        _diaryTagBtnCheck.value = false
    }

    fun getImage(){
        compositeDisposable.add(repository.selectDB(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                Log.e("getImage ::", "성공")
                _diaryImageUri.value = it.imageUri }
            .doOnError { Log.e("getImage ::", "실패") }
            .subscribe())
    }

    fun getUri(uri: Uri){ //
        _diaryImageUri.value = uri.toString()
    }

    fun createDiarysetting(){ // 다이어리 생성 버튼 누른 시간.
        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)

        _createDay.value = sdf
        _createDiaryCoupleDay.value = repository.getDateday()
        _createDiaryID.value = repository.getDBsize()
    }

    fun changedButtonCheck(view: View){ // 버튼 상태 확인
        when(view.tag){
            "touch" -> { _diaryTouchBtnCheck.value = _diaryTouchBtnCheck.value?.not() }
            "tag" -> { _diaryTagBtnCheck.value = _diaryTagBtnCheck.value?.not() }
            "trash" -> { _diaryTrashBtnCheck.value = _diaryTrashBtnCheck.value?.not() }
        }
    }

    fun createDiary(latLng: LatLng){ // Room 저장, observe를 통해 Marker 생성.
        if(_diaryImageUri.value != null && diaryTitle.value != null && diaryContent.value != null) {
            compositeDisposable
                .add(
                    repository.insertDB(
                        RoomDiaryEntity(
                            createDiaryID.value!!,
                            _diaryImageUri.value!!,
                            diaryTitle.value!!,
                            diaryContent.value!!,
                            _createDay.value!!,
                            latLng.longitude.toLong(),
                            latLng.latitude.toLong()
                        )
                    )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete {
                            toast("성공")
                            Log.e("createDiary ::", "성공")
                        }
                        .doOnError { Log.e("createDiary ::", "실패") }
                        .subscribe())
        }
        else if(_diaryImageUri.value == null){
            toast("이미지를 설정해주세요.")
        }
        else if(diaryTitle.value == null){
            toast("제목을 입력해주세요.")
        }
        else if(diaryContent.value == null){
            toast("내용을 입력해주세요.")
        }
    }
}