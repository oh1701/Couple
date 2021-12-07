package com.project.myapplication.ui.travel.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.databinding.FragmentTravelDiaryBinding
import com.project.myapplication.model.font.FontBindSettingModel
import com.project.myapplication.ui.dialog.view.FontDialogFragment
import com.project.myapplication.ui.dialog.view.WarningDialogFragment
import com.project.myapplication.ui.travel.viewmodel.TravelDiaryViewModel
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import com.project.myapplication.ui.travel.viewpager.ViewPagerDiaryImageFragment
import com.project.myapplication.ui.travel.viewpager.ViewPagerFullScreenImageFragment
import com.project.myapplication.utils.*
import com.project.myapplication.utils.customobserver.CustomObserver
import com.project.myapplication.utils.customobserver.EventObserver
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class TravelDiaryFragment(): BaseFragment<FragmentTravelDiaryBinding, TravelDiaryViewModel>() {
    override val layoutResourceId: Int = R.layout.fragment_travel_diary
    override val thisViewModel: TravelDiaryViewModel by viewModel()
    private val sharedActivityViewModel: TravelViewModel by sharedViewModel()
    private val warningDialogFragment: WarningDialogFragment by inject()
    private val photoClass: PhotoClass by inject()
    private val getGeoCoder:GeoCoder by inject()
    private lateinit var startForResultAlbum: ActivityResultLauncher<Intent>
    private lateinit var startForResultCamera: ActivityResultLauncher<Uri>
    private lateinit var cameraFileUri: Uri
    private lateinit var diaryOnBackPressed:OnBackPressedCallback
    private lateinit var customCallback:EventCustomCallback
    private var fontSettingModel:FontBindSettingModel? = null
    private val fontCustomEvent:(FontBindSettingModel) -> Unit = { setting ->
        fontSettingModel = setting
        thisViewModel.fontSetting(setting)
        thisViewModel.fontTypefaceToString.value = TextTypeFace().fontToString(setting.fontTypeFace!!, requireContext())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        diaryOnBackPressed = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(thisViewModel.closeDiary()) {
                    supportFragmentManager.popBackStack()
                }
                else {
                    if(!warningDialogFragment.isAdded) {
                        warningDialogFragment.show(supportFragmentManager, "closeDiary")
                    }
                    else{
                        warningDialogFragment.dismiss()
                        supportFragmentManager.popBackStack()
                    }
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, diaryOnBackPressed)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startActivityForResult() // oncreate 선언.
    }

    override fun initView() {
        binding.travelDiaryViewModel = thisViewModel
        binding.travelDiaryFragment = this

        customCallback = EventCustomCallback(fontCustomEvent)
        customCallback.setChanged()
        thisViewModel.getMetrics(resources.displayMetrics)

        if(arguments?.getInt("markerID") == 9999999 || arguments?.getInt("markerID") == null){
            thisViewModel.createDiarysetting(sharedActivityViewModel.myLocationLatLng.value)} // 다이어리 초기 설정해주기.
        else{
            thisViewModel.getDiary(arguments?.getInt("markerID")!!) } // 마커 클릭을 통해 들어온 것인지를 우선 파악. null이면 마커 클릭 아님
    }

    override fun initObserve() {
        thisViewModel.toastLiveData.observe(viewLifecycleOwner, EventObserver{ event -> // 완료, 에러용 토스트
            toast(event)
        })

        thisViewModel.diaryCompleteButton.observe(viewLifecycleOwner, EventObserver{
            supportFragmentManager.popBackStack()
        })

        thisViewModel.createDiaryLatLng.observe(viewLifecycleOwner, {
            thisViewModel.getGeoCoder(getGeoCoder.getGeoCoder(it))
        })

        thisViewModel.fontUpdateComplete.observe(viewLifecycleOwner, {
            val v = thisViewModel
            fontSettingModel = FontBindSettingModel(v.fontletterSpacing.value, v.fontlineSpacing.value,
            v.fontTypedSizeValue.value, v.fontcolorHex.value, v.fontTypeFace.value)
        })

        thisViewModel.createMarkerEvent.observe(viewLifecycleOwner, EventObserver{
            sharedActivityViewModel.newCreateMarker(thisViewModel.createDiaryID.value!!)
            supportFragmentManager.popBackStack()
        })

        thisViewModel.diaryTouchBtnCheck.observe(viewLifecycleOwner, CustomObserver{ boolean ->
            thisViewModel.viewEnabledValue(boolean.not()) // 버튼이 켜지면 Enabled false 처리해야함.
        })

        thisViewModel.removeWarningDialog.observe(viewLifecycleOwner, EventObserver{
            if(it){
                warningDialogFragment.show(supportFragmentManager, "removeDiary")
            }
        })

        thisViewModel.fontTypefaceToString.observe(viewLifecycleOwner, {
            thisViewModel.getFontTypeface(TextTypeFace().stringToFont(it, requireContext()))
        })

        thisViewModel.diaryFontBtnCheck.observe(viewLifecycleOwner, EventObserver{
            FontDialogFragment.newInstance(customCallback, fontSettingModel)
                .show(supportFragmentManager, this.javaClass.simpleName)
        })
    }

    fun cameraOpen(){ // startForResultCamera 의 registerForActivityResult가 AppCompat 상속 아니면 안불러짐
        if(thisViewModel.diaryEnabled.value == true) { // 살펴보기 기능 꺼져있을 경우
            val option = arrayOf<CharSequence>("카메라로 촬영하기", "앨범에서 불러오기")
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setItems(option) { dialog, item ->
                if (option[item] == "카메라로 촬영하기") {
                    cameraFileUri = photoClass.getImage()
                    startForResultCamera.launch(cameraFileUri)
                } else {
                    startForResultAlbum.launch(photoClass.albumMultipleIntent())
                }
            }
            dialog.show()
        }
    }

    fun fullScreenViewPagerOpen(){
        supportFragmentManager
            .beginTransaction()
            .addToBackStack("Map")
            .add(R.id.fragment_layout, ViewPagerFullScreenImageFragment.newInstance(thisViewModel.imageViewPagerNumber.value!!, thisViewModel.diaryImageUri.value!!))
            .commit()
    }

    private fun startActivityForResult(){
        //해당 함수는 oncreate에서 선언해줘야함.
        startForResultCamera = registerForActivityResult(ActivityResultContracts.TakePicture()){ check ->
            if(check) {
                thisViewModel.getUri(cameraFileUri)
            }
        }

        startForResultAlbum = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            photoClass.albumPictureResult(result, thisViewModel)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        diaryOnBackPressed.remove()
    }

    companion object{
        private const val markerID = "markerID"
        private const val page = "page"
        private const val size = "size"

        fun newInstance(myMarkerID: Int, myPage:Int): TravelDiaryFragment {
            val f = TravelDiaryFragment()

            val args = Bundle()
            args.putInt(markerID, myMarkerID)
            args.putInt(page, myPage)

            f.arguments = args

            return f
        }
    }
}