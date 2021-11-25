package com.project.myapplication.ui.travel.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.databinding.FragmentTravelDiaryBinding
import com.project.myapplication.model.font.FontBindSetting
import com.project.myapplication.ui.dialog.view.FontDialogFragment
import com.project.myapplication.ui.dialog.view.WarningDialogFragment
import com.project.myapplication.ui.travel.viewmodel.TravelDiaryViewModel
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import com.project.myapplication.utils.EventCustomCallback
import com.project.myapplication.utils.PhotoClass
import com.project.myapplication.utils.observer.CustomObserver
import com.project.myapplication.utils.observer.EventObserver
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class TravelDiaryFragment(): BaseFragment<FragmentTravelDiaryBinding, TravelDiaryViewModel>() {
    override val layoutResourceId: Int = R.layout.fragment_travel_diary
    override val thisViewModel: TravelDiaryViewModel by viewModel()
    private val sharedActivityViewModel: TravelViewModel by sharedViewModel()
    private val warningDialogFragment: WarningDialogFragment by inject()
    private val photoClass: PhotoClass by inject()
    private lateinit var startForResultAlbum: ActivityResultLauncher<Intent>
    private lateinit var startForResultCamera: ActivityResultLauncher<Uri>
    private lateinit var cameraFileUri: Uri
    private lateinit var diaryOnBackPressed:OnBackPressedCallback
    private lateinit var customCallback:EventCustomCallback
    private var fontSetting:FontBindSetting? = null
    private val customEvent:(FontBindSetting) -> Unit = { setting ->
        fontSetting = setting
        thisViewModel.getFontSetting(fontSetting!!)
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
                        warningDialogFragment.show(supportFragmentManager, "diary")
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
        customCallback = EventCustomCallback(customEvent)
        customCallback.setChanged()
        
    }

    override fun initView() {
        binding.travelDiaryViewModel = thisViewModel
        binding.travelMainViewModel = sharedActivityViewModel
        binding.travelDiaryFragment = this

        thisViewModel.fontSaveTextWatcherFunction()
        this.tag?.toIntOrNull()?.let{ id -> thisViewModel.getDiary(id) } // 마커 클릭을 통해 들어온 것인지를 우선 파악.
        thisViewModel.createDiarysetting(sharedActivityViewModel.myLocationLatLng.value) // 다이어리 초기 설정해주기.
    }

    override fun initObserve() {
        thisViewModel.toastLiveData.observe(viewLifecycleOwner, EventObserver{ event -> // 완료 시 에러용 토스트
            Toast.makeText(requireContext(), event, Toast.LENGTH_SHORT).show()
        })

        thisViewModel.diaryCompleteButton.observe(viewLifecycleOwner, EventObserver{
            supportFragmentManager.popBackStack()
        })

        thisViewModel.createMarkerEvent.observe(viewLifecycleOwner, EventObserver{
            sharedActivityViewModel.newCreateMarker(thisViewModel.createDiaryID.value!!)
            supportFragmentManager.popBackStack()
        })

        thisViewModel.diaryTouchBtnCheck.observe(viewLifecycleOwner, CustomObserver{ boolean ->
            thisViewModel.viewEnabledValue(boolean.not()) // 버튼이 켜지면 Enabled false 처리해야함.
        })

        thisViewModel.diaryFontBtnCheck.observe(viewLifecycleOwner, EventObserver{
            FontDialogFragment().apply{
                arguments = Bundle().apply{
                    putParcelable("listener", customCallback)
                    putParcelable("FontSetting", fontSetting)
                }
            }.show(supportFragmentManager, this.javaClass.simpleName)
        })
    }

    fun cameraOpen(){ // startForResultCamera 의 registerForActivityResult가 AppCompat 상속 아니면 안불러짐
        val option = arrayOf<CharSequence>("카메라로 촬영하기", "앨범에서 불러오기")
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setItems(option) { dialog, item ->
            if (option[item] == "카메라로 촬영하기") {
                cameraFileUri = photoClass.getImage()
                startForResultCamera.launch(cameraFileUri)
            }
            else{
                startForResultAlbum.launch(photoClass.albumPictureIntent())
            }
        }
        dialog.show()
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
}
