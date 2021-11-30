package com.project.myapplication.ui.travel.viewmodel

import android.content.res.ColorStateList
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.db.room.entity.RoomDiaryEntity
import com.project.myapplication.db.room.entity.RoomFontEntity
import com.project.myapplication.model.DiaryTagModel
import com.project.myapplication.model.font.FontBindSettingModel
import com.project.myapplication.ui.travel.repository.TravelDiaryRepository
import com.project.myapplication.utils.FontToHtml
import com.project.myapplication.utils.customobserver.CustomObserve
import com.project.myapplication.utils.customobserver.Event
import com.project.myapplication.utils.customobserver.RecyclerListLiveData
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
    private val _checkInsertUpdate = MutableLiveData(Event(true)) // true면 Insert false 면 Update문 실행, default는 true

    // 다이어리 기본
    private val _createDiaryCoupleDay = MutableLiveData<String>()
    val createDiaryCoupleDay:LiveData<String> = _createDiaryCoupleDay
    private val _diaryViewVisibility = MutableLiveData<Int>()
    val diaryViewVisibility:LiveData<Int> = _diaryViewVisibility
    private val _diaryFontBtnCheck = MutableLiveData<Event<Boolean>>()
    val diaryFontBtnCheck:LiveData<Event<Boolean>> = _diaryFontBtnCheck
    private val _diaryTrashBtnCheck = MutableLiveData<CustomObserve<Boolean>>()
    val diaryTrashBtnCheck:LiveData<CustomObserve<Boolean>> = _diaryTrashBtnCheck
    private val _diaryTouchBtnCheck = MutableLiveData<CustomObserve<Boolean>>()
    val diaryTouchBtnCheck:LiveData<CustomObserve<Boolean>> = _diaryTouchBtnCheck
    private val _diaryTagBtnCheck = MutableLiveData<CustomObserve<Boolean>>()
    val diaryTagBtnCheck:LiveData<CustomObserve<Boolean>> = _diaryTagBtnCheck
    private val _diaryEnabled = MutableLiveData<Boolean>()
    val diaryEnabled:LiveData<Boolean> = _diaryEnabled
    private val _fontSaveCallback = MutableLiveData<((String?) -> Unit)?>()
    val fontSaveCallback:LiveData<((String?) -> Unit)?> = _fontSaveCallback

    // 다이어리 입력
    private val _diaryImageUri = MutableLiveData<String>(null)
    val diaryImageUri : LiveData<String> = _diaryImageUri
    val diaryTitle = MutableLiveData<String>()
    val diaryContent = MutableLiveData<String>()

    //다이어리 저장`
    private val _diaryCompleteButton = MutableLiveData<Event<Boolean>>()
    val diaryCompleteButton:LiveData<Event<Boolean>> = _diaryCompleteButton
    private val _createMarkerEvent = MutableLiveData<Event<Boolean>>()
    val createMarkerEvent:LiveData<Event<Boolean>> = _createMarkerEvent

    //폰트
    private var otherHtml = ""
    private val _fontSettingInput = MutableLiveData(FontBindSettingModel(0.0f, 1.0f, 16.0f, ColorStateList.valueOf(-570425344)))
    val fontSettingModelInput:LiveData<FontBindSettingModel> = _fontSettingInput

    //리사이클러뷰
    val recyclerList = RecyclerListLiveData<DiaryTagModel>()


    init{
        _diaryTrashBtnCheck.value = CustomObserve(content = false, firstInitialization = true)
        _diaryTouchBtnCheck.value = CustomObserve(content = false, firstInitialization = true)
        _diaryTagBtnCheck.value = CustomObserve(content = false, firstInitialization = true)
        _diaryEnabled.value = true
        recyclerList.listLiveData()
    }

    // DB 관련

    fun getDiary(id:Int){ // 마커 클릭을 통해서 다이어리 생성
        _createDiaryID.value = id // 존재하던 마커를 수정하기 위해 현재 id를 받아온 ID로 설정한다

        compositeDisposable.add(repository.getDiaryID(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _diaryImageUri.value = it.imageUri
                diaryTitle.value = it.title
                diaryContent.value = it.content
                _checkInsertUpdate.value = Event(false)
            }
            .doOnError { 
                Log.e("getDiary ::", "실패")
                toast("Error : 올바른 초기 값을 가져오지 못하였습니다. ")
            }
            .subscribe()
        )
    }

    fun roomSaveDiary(){ // Room 저장, value에 ID 저장 후 observe를 통해 Marker 생성.
        if (diaryTitle.value != null && diaryContent.value != null && diaryImageUri.value != null) {
            compositeDisposable
                .add(
                    if(_checkInsertUpdate.value!!.peekContent()) {
                        repository.insertDiaryDB(
                            RoomDiaryEntity(
                                createDiaryID.value!!,
                                diaryImageUri.value,
                                diaryTitle.value!!,
                                diaryContent.value!!,
                                createDiaryDay.value!!,
                                createDiaryLatLng.value?.longitude ?: 0.0, // 서울 위경도로 바꾸기.
                                createDiaryLatLng.value?.latitude ?: 0.0
                            )
                        )
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnComplete {
                                toast("성공")
                                _createMarkerEvent.value = Event(true)
                                roomSaveFont()
                                Log.e("createDiary insert ::", "성공 ${createDiaryID.value}")
                            }
                            .doOnError {
                                Log.e("createDiary insert ::", "실패")
                                toast("Error :  일기 저장에 실패하였습니다.")
                            }
                            .subscribe()
                    }
                else {
                        repository.updateDiaryDB(
                            RoomDiaryEntity(
                                createDiaryID.value!!,
                                diaryImageUri.value,
                                diaryTitle.value!!,
                                diaryContent.value!!,
                                createDiaryDay.value!!,
                                createDiaryLatLng.value?.longitude ?: 0.0, // 서울 위경도로 바꾸기.
                                createDiaryLatLng.value?.latitude ?: 0.0
                            )
                        )
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnComplete {
                                toast("수정 완료")
                                _diaryCompleteButton.value = Event(true)
                                roomSaveFont()
                                Log.e("createDiary update ::", "성공 ${createDiaryID.value}")
                            }
                            .doOnError {
                                Log.e("createDiary update ::", "실패")
                                toast("Error :  일기 저장에 실패하였습니다.")
                            }
                            .subscribe()
                    }
                )
        } else if(diaryImageUri.value == null){
            toast("1장 이상의 이미지를 등록해주세요.")
        } else if (diaryTitle.value == null) {
            toast("제목을 입력해주세요.")
        } else if (diaryContent.value == null) {
            toast("내용을 입력해주세요.")
        }
    }

    private fun roomSaveFont(){
        if(_checkInsertUpdate.value!!.peekContent()) {
            repository.insertFontDB(
                RoomFontEntity(
                    createDiaryID.value!!,
                    null,
                    otherHtml + FontToHtml().endHtml,
                    fontSettingModelInput.value?.letterSpacing,
                    fontSettingModelInput.value?.lineSpacing,
                    fontSettingModelInput.value?.fontTypedSizeValue
            ))
        }
        else{
            repository.updateFontDB(
                RoomFontEntity(
                    createDiaryID.value!!,
                    null,
                    otherHtml + FontToHtml().endHtml,
                    fontSettingModelInput.value?.letterSpacing,
                    fontSettingModelInput.value?.lineSpacing,
                    fontSettingModelInput.value?.fontTypedSizeValue
                ))
        }
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
                toast("Error : 올바른 ID 값을 가져오지 못하였습니다.")
            }
            .subscribe())
    }

    // DB 호출 함수 관련 or 뷰 관련

    fun getUri(uri: Uri){ // 갤러리 or 카메라에서 이미지 가져오기
        _diaryImageUri.value = uri.toString()
    }

    fun createDiarysetting(latLng: LatLng?){ // 다이어리 생성 버튼 누른 시간.
        _createDiaryCoupleDay.value = repository.getDateday()
        _createDiaryLatLng.value = latLng
        getCreateDay()

        if(createDiaryID.value == null){ // 현재 ID가 지정되어 있지 않은 경우에만 (마커 클릭을 통해 들어온 것이 아닐경우에만) 설정.
            getDBsizeID()
        }
    }

    fun viewEnabledValue(boolean:Boolean){ // 뷰 Enabled 값 터치버튼 활성화에 따라 나누기
        _diaryEnabled.value = boolean
        when(boolean.not()){
            false -> toast("터치 금지 해제")
            true -> toast("터치 금지 활성화")
        }
    }

    fun changedButtonCheck(view: View){ // 버튼 상태 확인
        when(view.tag){
            "touch" -> _diaryTouchBtnCheck.value = CustomObserve(_diaryTouchBtnCheck.value?.peekContent()!!.not(), false)
            "tag" -> _diaryTagBtnCheck.value = CustomObserve(_diaryTagBtnCheck.value?.peekContent()!!.not(), false)
            "trash" -> _diaryTrashBtnCheck.value = CustomObserve(_diaryTrashBtnCheck.value?.peekContent()!!.not(), false)
            "font" -> _diaryFontBtnCheck.value = Event(true)
        }
    }

    fun closeDiary():Boolean{
        return diaryTitle.value == null && diaryContent.value == null && diaryImageUri.value == null
    }

    fun getFontSetting(fontSettingModel: FontBindSettingModel){
        _fontSettingInput.value = fontSettingModel
    }

    private fun getCreateDay(){ // 다이어리 생성 날짜
        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)

        _createDiaryDay.value = sdf
    }

    fun fontSaveTextWatcherFunction(){
        _fontSaveCallback.value = {
            if (it != null) {
                otherHtml = it
                Log.e("otherHtml", otherHtml + FontToHtml().endHtml)
            }
        }
    }

    fun addTagRecyclerlist(){
        recyclerList.add(DiaryTagModel("# ", "1"))
    }
}

/** EditText Spannable 여러개 등록 안되어서 Data class 만들고 계산식 사용한 함수 만들어서 다 계산하기*/