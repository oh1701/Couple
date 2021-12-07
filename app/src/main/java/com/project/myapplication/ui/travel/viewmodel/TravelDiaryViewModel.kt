package com.project.myapplication.ui.travel.viewmodel

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.net.Uri
import android.text.Html
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.data.room.entity.RoomDiaryEntity
import com.project.myapplication.data.room.entity.RoomFontEntity
import com.project.myapplication.model.DiaryTagModel
import com.project.myapplication.model.font.FontBindSettingModel
import com.project.myapplication.model.font.FontTypeFace
import com.project.myapplication.ui.travel.repository.TravelDiaryRepository
import com.project.myapplication.utils.FontToHtml
import com.project.myapplication.utils.customobserver.CustomObserve
import com.project.myapplication.utils.customobserver.Event
import com.project.myapplication.utils.customobserver.ListMutableLiveData
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
    private val _createDiaryCoupleDay = MutableLiveData<String>()
    val createDiaryCoupleDay:LiveData<String> = _createDiaryCoupleDay
    private val _createDiaryDay = MutableLiveData<String>()
    val createDiaryDay:LiveData<String> = _createDiaryDay
    private val _createDiaryID = MutableLiveData<Int>() // 다이어리 생성 버튼 클릭 후 진입 시 생성되는 ID. List 마지막 인덱스 ID + 1
    val createDiaryID:LiveData<Int> = _createDiaryID
    private val _createDiaryLatLng = MutableLiveData<LatLng>()
    val createDiaryLatLng:LiveData<LatLng> = _createDiaryLatLng
    private val _checkInsertUpdate = MutableLiveData(Event(true)) // true면 Insert false 면 Update문 실행, default는 true

    // 다이어리 기본
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
    private val _getNowLocation = MutableLiveData<String>()
    val getNowLocation:LiveData<String> = _getNowLocation

    // 다이어리 입력
    val diaryImageUri = ListMutableLiveData<String>()
    val diaryTitle = MutableLiveData<String>()
    val diaryContent = MutableLiveData<String>()

    // 저장 or 삭제 버튼 변경
    private val _diarySaveOrRemoveButton = MutableLiveData<String>("저장하기")
    val diarySaveOrRemoveButton:LiveData<String> = _diarySaveOrRemoveButton

    //다이어리 저장
    private val _diaryCompleteButton = MutableLiveData<Event<Boolean>>()
    val diaryCompleteButton:LiveData<Event<Boolean>> = _diaryCompleteButton
    private val _createMarkerEvent = MutableLiveData<Event<Boolean>>()
    val createMarkerEvent:LiveData<Event<Boolean>> = _createMarkerEvent

    //다이어리 삭제
    private val _diaryRemoveButton = MutableLiveData<Event<Boolean>>()
    val diaryRemoveButton:LiveData<Event<Boolean>> = _diaryRemoveButton
    private val _removeMarkerEvent = MutableLiveData<Event<Boolean>>()
    val removeMarkerEvent:LiveData<Event<Boolean>> = _removeMarkerEvent
    private val _removeWarningDialog = MutableLiveData(Event(false))
    val removeWarningDialog:LiveData<Event<Boolean>> = _removeWarningDialog

    //폰트 적용
    private val _fontletterSpacing = MutableLiveData<Float>(0.0f)
    val fontletterSpacing :LiveData<Float> = _fontletterSpacing
    private val _fontlineSpacing = MutableLiveData<Float>(1.0f)
    val fontlineSpacing :LiveData<Float> = _fontlineSpacing
    private val _fontTypedSizeValue = MutableLiveData<Float>(16.0f)
    val fontTypedSizeValue:LiveData<Float> = _fontTypedSizeValue
    private val _fontSize = MutableLiveData<Float>()
    val fontSize:LiveData<Float> = _fontSize
    private val _fontcolorHex = MutableLiveData<ColorStateList>(ColorStateList.valueOf(-570425344))
    val fontcolorHex:LiveData<ColorStateList> = _fontcolorHex
    private val _fontTypeFace = MutableLiveData<Typeface>(Typeface.DEFAULT)
    val fontTypeFace:LiveData<Typeface> = _fontTypeFace
    
    //TypeFace 변경용
    val fontTypefaceToString = MutableLiveData<String>("기본")

    //리사이클러뷰
    val recyclerList = ListMutableLiveData<DiaryTagModel>()

    //뷰페이저 확대, 지우기 버튼 visibility
    private val _viewPagerFullScreenButtonVisibility = MutableLiveData<Int>(View.GONE)
    val viewPagerFullScreenButtonVisibility:LiveData<Int> = _viewPagerFullScreenButtonVisibility

    //마커 클릭 후 콘텐츠 변화 상태 확인
    private val _updateImageValue = ListMutableLiveData<String>()
    private val _updateTitleValue = MutableLiveData<String>()
    private val _updateContentValue = MutableLiveData<String>()
    val fontUpdateComplete = MutableLiveData<Boolean>()

    //현재 이미지 뷰페이저 위치
    private val _imageViewPagerNumber = MutableLiveData<Int>(0)
    val imageViewPagerNumber:LiveData<Int> = _imageViewPagerNumber

    //콜백
    val imageViewPagerNumberCallback = MutableLiveData<(Int) -> Unit> { ImagePosition ->
        _imageViewPagerNumber.value = ImagePosition
        if(diaryImageUri.value?.isNullOrEmpty() == true || diaryTrashBtnCheck.value?.peekContent() == true){ // 이미지 존재 유무에 따라 설정.
            _viewPagerFullScreenButtonVisibility.value = View.GONE
        }
        else if(diaryImageUri.value?.isNullOrEmpty() == false){
            _viewPagerFullScreenButtonVisibility.value = View.VISIBLE
        }
    }

    //초기화
    init{
        _diaryTrashBtnCheck.value = CustomObserve(content = false, firstInitialization = true)
        _diaryTouchBtnCheck.value = CustomObserve(content = false, firstInitialization = true)
        _diaryTagBtnCheck.value = CustomObserve(content = false, firstInitialization = true)
        _diaryEnabled.value = true
        recyclerList.listLiveData()
        diaryImageUri.listLiveData()
        _updateImageValue.listLiveData()
    }

    // DB 관련
    fun getDiary(id:Int){ // 마커 클릭을 통해서 다이어리 생성
        _createDiaryID.value = id // 존재하던 마커를 수정하기 위해 현재 id를 받아온 ID로 설정한다

        getDiaryEntity(id)
        getFontEntity(id)
    }

    private fun getDiaryEntity(id:Int){
        compositeDisposable.add(repository.getDiaryID(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                diaryImageUri.addAll(it.imageUri)
                diaryTitle.value = it.title
                diaryContent.value = it.content
                _createDiaryDay.value = it.createDay
                _createDiaryCoupleDay.value = it.coupleDay
                _createDiaryLatLng.value = LatLng(it.latitude, it.longitude)
                _checkInsertUpdate.value = Event(false)
                _diaryTouchBtnCheck.value = CustomObserve(_diaryTouchBtnCheck.value?.peekContent()!!.not(), false)

                //업데이트 확인용
                _updateImageValue.addAll(it.imageUri)
                _updateTitleValue.value = diaryTitle.value
                _updateContentValue.value = diaryContent.value
            }
            .doOnError {
                Log.e("getDiaryEntity ::", "실패 이유 $:$it")
                toast("Error : 기본 데이터 값을 가져오지 못하였습니다. ")
            }
            .subscribe()
        )
    }

    private fun getFontEntity(id:Int){
        compositeDisposable.add(repository.selectFontDB(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _fontlineSpacing.value = it.lineSpacing
                _fontletterSpacing.value = it.letterSpacing
                _fontcolorHex.value = ColorStateList.valueOf(it.colorInt)
                fontTypefaceToString.value = it.textTypeFace
                _fontTypedSizeValue.value = it.fontTypedSizeValue
                _fontSize.value = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, fontTypedSizeValue.value!!, metrics.value)
                fontUpdateComplete.value = true
            }
            .doOnError {
                Log.e("getFontEntity ::", "실패 이유 $:$it")
                toast("Error : 폰트 데이터 값을 가져오지 못하였습니다. ")
            }
            .subscribe()
        )
    }

    fun saveORremoveButtonClick(v:View){
        if(v.tag == "저장하기"){
            roomSaveDiary()
        }
        else{ // 삭제하기
            roomRemoveDiary()
            Log.e("콘텐트는", diaryContent.value.toString())
        }
    }

    private fun roomRemoveDiary(){ // Room 삭제, value에 ID 저장 후 observe를 통해 ID에 맞는 Marker 삭제
//        _removeWarningDialog.value = Event(true)
    }

    private fun roomSaveDiary(){ // Room 저장, value에 ID 저장 후 observe를 통해 Marker 생성.
        if (diaryTitle.value != null && diaryContent.value != null && diaryImageUri.value != null) {
            if(_checkInsertUpdate.value!!.peekContent()) {
                roomInsertFont()
                roomDiaryInsertDB()
            }
            else {
                roomUpdateFont()
                roomDiaryUpdateDB()
            }
        } else if(diaryImageUri.value == null){
            toast("1장 이상의 이미지를 등록해주세요.")
        } else if (diaryTitle.value == null) {
            toast("제목을 입력해주세요.")
        } else if (diaryContent.value == null) {
            toast("내용을 입력해주세요.")
        }
    }

    private fun roomDiaryInsertDB(){
        compositeDisposable
            .add(repository.insertDiaryDB(
                RoomDiaryEntity(
                    createDiaryID.value!!,
                    diaryImageUri.value,
                    diaryTitle.value!!,
                    diaryContent.value!!,
                    createDiaryDay.value!!,
                    createDiaryCoupleDay.value!!,
                    createDiaryLatLng.value?.longitude ?: 0.0, // 서울 위경도로 바꾸기.
                    createDiaryLatLng.value?.latitude ?: 0.0
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    toast("일기 저장 완료")
                    _createMarkerEvent.value = Event(true)
                    Log.e("createDiary insert ::", "성공 ${createDiaryID.value}")
                }
                .doOnError {
                    Log.e("createDiary insert ::", "실패")
                    toast("Error : 일기 저장에 실패하였습니다.")
                }
                .subscribe()
            )
    }

    private fun roomDiaryUpdateDB(){
        compositeDisposable.add(
            repository.updateDiaryDB(
                RoomDiaryEntity(
                    createDiaryID.value!!,
                    diaryImageUri.value,
                    diaryTitle.value!!,
                    diaryContent.value!!,
                    createDiaryDay.value!!,
                    createDiaryCoupleDay.value!!,
                    createDiaryLatLng.value?.longitude ?: 0.0, // 서울 위경도로 바꾸기.
                    createDiaryLatLng.value?.latitude ?: 0.0
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    toast("일기 수정 완료")
                    _diaryCompleteButton.value = Event(true)
                    Log.e("createDiary update ::", "성공 ${createDiaryID.value}")
                }
                .doOnError {
                    Log.e("createDiary update ::", "실패")
                    toast("Error : 일기 수정에 실패하였습니다.")
                }
                .subscribe()
        )
    }

    private fun roomInsertFont(){
        compositeDisposable.add(
            repository.insertFontDB(
                RoomFontEntity(
                    createDiaryID.value!!,
                    fontTypefaceToString.value,
                    fontcolorHex.value!!.defaultColor,
                    fontletterSpacing.value,
                    fontlineSpacing.value,
                    fontTypedSizeValue.value
                ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    Log.e("insertFontDB ::", "폰트 저장 완료")
                }
                .doOnError {
                    Log.e("insertFontDB ::", "폰트 저장 에러")
                    toast("Error : 폰트 저장에 실패하였습니다.")
                }
                .subscribe()
        )
    }

    private fun roomUpdateFont(){
        compositeDisposable.add(
        repository.updateFontDB(
            RoomFontEntity(
                createDiaryID.value!!,
                fontTypefaceToString.value,
                fontcolorHex.value!!.defaultColor,
                fontletterSpacing.value,
                fontlineSpacing.value,
                fontTypedSizeValue.value
            ))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                Log.e("updateFontDB ::", "폰트 수정 완료")
            }
            .doOnError {
                Log.e("updateFontDB ::", "폰트 수정 에러")
                toast("Error : 폰트 수정에 실패하였습니다.")
            }
            .subscribe()
        )
    }
    private fun getDBsizeID(){
        compositeDisposable.add(repository.getDiaryDB()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _createDiaryID.value =
                    if(it.isEmpty()) {
                        Log.e("getDBsize ::", "DB로 사용될 ID는 ${1}")
                        1
                    }
                    else {
                        Log.e("getDBsize ::", "DB로 사용될 ID는 ${it.last().id + 1}")
                        it.last().id + 1
                    }  // 리스트의 마지막 ID + 1로 저장.
            }
            .doOnError {
                Log.e("getDBsizeID ::", "실패")
                toast("Error : 올바른 ID 값을 가져오지 못하였습니다.")
            }
            .subscribe())
    }

    // DB 호출 함수 관련 or 뷰 관련
    fun getUri(uri: Uri){ // 카메라에서 이미지 가져오기
        diaryImageUri.add(uri.toString())
    }

    fun getClipData(imageClipData: Intent?){
        if(imageClipData?.clipData == null){ // 불러온 이미지가 한 장이면
            diaryImageUri.add(imageClipData?.data.toString())
        }
        else{
            for (i in 0 until imageClipData.clipData!!.itemCount) {
                diaryImageUri.add(imageClipData.clipData!!.getItemAt(i).uri.toString())
            }
        }
    }

    fun createDiarysetting(latLng: LatLng?){ // 다이어리 생성 버튼 누른 시간.
        _fontSize.value = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, fontTypedSizeValue.value!!, metrics.value)
        if(createDiaryID.value == null){ // 현재 ID가 지정되어 있지 않은 경우에만 (마커 클릭을 통해 들어온 것이 아닐경우에만) 설정.
            _createDiaryCoupleDay.value = repository.getDateday()
            _createDiaryLatLng.value = latLng
            getCreateDay()
            getDBsizeID()
        }
    }

    fun viewEnabledValue(boolean:Boolean){ // 뷰 Enabled 값 터치버튼 활성화에 따라 나누기
        _diaryEnabled.value = boolean
//        when(boolean.not()){
//            false -> toast("수정 금지모드 해제")
//            true -> toast("수정 금지모드")
//        }
    }

    fun changedButtonCheck(view: View){ // 버튼 상태 확인
        if(view.tag == "touch") {
            _diaryTouchBtnCheck.value =
                CustomObserve(_diaryTouchBtnCheck.value?.peekContent()!!.not(), false)
        }

        if(diaryEnabled.value == true) {
            when (view.tag) {
                "tag" -> _diaryTagBtnCheck.value =
                    CustomObserve(_diaryTagBtnCheck.value?.peekContent()!!.not(), false)
                "trash" -> {
                    _diaryTrashBtnCheck.value = CustomObserve(_diaryTrashBtnCheck.value?.peekContent()!!.not(), false)
                    diarySaveOrRemoveButtonChange()
                }
                "font" -> _diaryFontBtnCheck.value = Event(true)
            }
        }
    }

    private fun diarySaveOrRemoveButtonChange(){
        if(_diaryTrashBtnCheck.value?.peekContent()!! || _diaryTrashBtnCheck.value?.peekContent() == null){ // 휴지통 버튼 켜져있으면
            _diarySaveOrRemoveButton.value = "삭제하기"
            _viewPagerFullScreenButtonVisibility.value = View.GONE
        }
        else{
            _diarySaveOrRemoveButton.value = "저장하기"

            if(!diaryImageUri.value.isNullOrEmpty())
                _viewPagerFullScreenButtonVisibility.value = View.VISIBLE
        }
    }
    
    fun closeDiary():Boolean{
        return if(_checkInsertUpdate.value!!.peekContent()) { // 다이어리 생성 통해서 온것이면
            diaryTitle.value == null && diaryContent.value == null && diaryImageUri.value.isNullOrEmpty()
        }
        else{ // 마커클릭 통해서 온것이면
            diaryTitle.value == _updateTitleValue.value &&
                    diaryContent.value == _updateContentValue.value &&
                        diaryImageUri.value == _updateImageValue.value
        }
    }

    private fun getCreateDay(){ // 다이어리 생성 날짜
        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)

        _createDiaryDay.value = sdf
    }

    fun fontSetting(setting: FontBindSettingModel){
        _fontlineSpacing.value = setting.lineSpacing
        _fontletterSpacing.value = setting.letterSpacing
        _fontcolorHex.value = setting.colorHex
        _fontTypedSizeValue.value = setting.fontTypedSizeValue
        _fontSize.value = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, setting.fontTypedSizeValue!!, metrics.value)
    }

    fun getFontTypeface(typeface: Typeface){
        _fontTypeFace.value = typeface
    }

    fun getGeoCoder(geoCoderString: String?){
        _getNowLocation.value = geoCoderString
    }

    fun addTagRecyclerlist(){
        if(diaryEnabled.value == true)
            recyclerList.add(DiaryTagModel("# ", "1"))
    }
}