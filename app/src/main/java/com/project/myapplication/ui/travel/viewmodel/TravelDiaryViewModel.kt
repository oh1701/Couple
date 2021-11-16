package com.project.myapplication.ui.travel.viewmodel

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.common.Event
import com.project.myapplication.data.room.entity.RoomDiaryEntity
import com.project.myapplication.ui.travel.repository.TravelDiaryRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

/** insertDB는 작성완료했을때 RoomDiaryEntity를 파라미터값으로 받는다.*/

class TravelDiaryViewModel(private val repository: TravelDiaryRepository):BaseViewModel() {
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable
    
    // 초기 세팅
    private val _createDiaryDay = MutableLiveData<String>()
    val createDiaryDay:LiveData<String> = _createDiaryDay
    private val _createDiaryID = MutableLiveData<Int>() // 다이어리 생성 버튼 클릭 후 진입 시 생성되는 ID. List 사이즈 + 1
    val createDiaryID:LiveData<Int> = _createDiaryID
    private val _createDiaryLatLng = MutableLiveData<LatLng>()
    val createDiaryLatLng:LiveData<LatLng> = _createDiaryLatLng
    
    // 다이어리 기본
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

    // 다이어리 입력
    private val _diaryImageUri = MutableLiveData<String>(null)
    val diaryImageUri : LiveData<String> = _diaryImageUri
    val diaryTitle = MutableLiveData<String>()
    val diaryContent = MutableLiveData<String>()

    //다이어리 저장
    private val _diarySaveID = MutableLiveData<Event<Int>>()
    val diarySaveID:LiveData<Event<Int>> = _diarySaveID
    private val _diaryCompleteButton = MutableLiveData<Event<Boolean>>()
    val diaryCompleteButton:LiveData<Event<Boolean>> = _diaryCompleteButton


    init{
        _diaryTrashBtnCheck.value = false
        _diaryTouchBtnCheck.value = false
        _diaryTagBtnCheck.value = false
    }

    fun getDiary(id:Int){
        compositeDisposable.add(repository.getDiaryID(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _diaryImageUri.value = it.imageUri
                diaryTitle.value = it.title
                diaryContent.value = it.content
            }
            .doOnError { 
                Log.e("getDiary ::", "실패")
                toast("올바른 초기 값을 가져오지 못하였습니다. \n Error : $it")
            }
            .subscribe()
        )
    }

    fun getUri(uri: Uri){ // 갤러리 or 카메라에서 이미지 가져오기
        _diaryImageUri.value = uri.toString()
    }

    fun createDiarysetting(latLng: LatLng?){ // 다이어리 생성 버튼 누른 시간.
        _createDiaryCoupleDay.value = repository.getDateday()
        _createDiaryLatLng.value = latLng
        getDBsizeID()
        getCreateDay()
    }

    fun changedButtonCheck(view: View){ // 버튼 상태 확인
        when(view.tag){
            "touch" -> { _diaryTouchBtnCheck.value = _diaryTouchBtnCheck.value?.not() }
            "tag" -> { _diaryTagBtnCheck.value = _diaryTagBtnCheck.value?.not() }
            "trash" -> { _diaryTrashBtnCheck.value = _diaryTrashBtnCheck.value?.not() }
        }
    }

    fun createDiary(){ // Room 저장, value에 ID 저장 후 observe를 통해 Marker 생성.
        if(diaryTitle.value != null && diaryContent.value != null) {
            compositeDisposable
                .add(
                    repository.insertDB(
                        RoomDiaryEntity(
                            createDiaryID.value!!,
                            diaryImageUri.value,
                            diaryTitle.value!!,
                            diaryContent.value!!,
                            createDiaryDay.value!!,
                            createDiaryLatLng.value?.longitude?.toLong() ?: 0.0.toLong(), // 서울 위경도로 바꾸기.
                            createDiaryLatLng.value?.latitude?.toLong() ?: 0.0.toLong()
                        )
                    )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete {
                            toast("성공")
                            _diarySaveID.value = Event(createDiaryID.value!!)
                            Log.e("createDiary ::", "성공 ${createDiaryID.value}")
                        }
                        .doOnError {
                            Log.e("createDiary ::", "실패")
                            toast("일기 저장에 실패하였습니다. \n Error : $it")
                        }
                        .subscribe())
        }
        else if(diaryTitle.value == null){
            toast("제목을 입력해주세요.")
        }
        else if(diaryContent.value == null){
            toast("내용을 입력해주세요.")
        }
    }

    fun closeDiary():Boolean{
        return diaryTitle.value == null && diaryContent.value == null
    }

    private fun getDBsizeID(){
        compositeDisposable.add(repository.getDiaryDB()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _createDiaryID.value = it.size + 1
                Log.e("getDBsize ::", "사이즈는 ${it.size + 1},\n 내용물은 $it")
            }
            .doOnError {
                Log.e("실패", "실패")
                toast("올바른 ID 값을 가져오지 못하였습니다. \n Error : $it")
            }
            .subscribe())
    }

    private fun getCreateDay(){
        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)

        _createDiaryDay.value = sdf
    }
}