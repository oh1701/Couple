package com.project.myapplication.ui.travel.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.text.HtmlCompat
import androidx.core.text.getSpans
import androidx.core.text.toSpannable
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.utils.observer.EventObserver
import com.project.myapplication.utils.PhotoClass
import com.project.myapplication.databinding.FragmentTravelDiaryBinding
import com.project.myapplication.model.FontSetting
import com.project.myapplication.ui.dialog.view.FontDialogFragment
import com.project.myapplication.ui.dialog.view.WarningDialogFragment
import com.project.myapplication.ui.travel.viewmodel.TravelDiaryViewModel
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import com.project.myapplication.utils.EventCustomCallback
import com.project.myapplication.utils.FontToHtml
import com.project.myapplication.utils.observer.CustomObserver
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Integer.parseInt
import java.util.regex.Matcher
import java.util.regex.Pattern

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
    private val customEvent:(FontSetting) -> Unit = { setting ->
        fontSetting(setting)
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

        thisViewModel.fontSaveTextWatcherFunction(binding.content)
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
        })

        thisViewModel.diaryTouchBtnCheck.observe(viewLifecycleOwner, CustomObserver{ boolean ->
            thisViewModel.viewEnabledValue(boolean.not()) // 버튼이 켜지면 Enabled false 처리해야함.
        })

        thisViewModel.diaryFontBtnCheck.observe(viewLifecycleOwner, EventObserver{
            FontDialogFragment().apply{
                arguments = Bundle().apply{
                    putParcelable("listener", customCallback)
                }
            }.show(supportFragmentManager, this.javaClass.simpleName)
        })

        thisViewModel.contentFont.observe(this, {
            binding.title.text = it
        })
    }

    fun cameraOpen(){
        // 카메라면 이거.
        cameraFileUri = photoClass.getImage()
        startForResultCamera.launch(cameraFileUri)

        // 앨범이면 이거
//        startForResultAlbum.launch(photoClass.albumPictureIntent())
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

    private fun fontSetting(fontSetting: FontSetting){ // FontDialogFragment로 전달된 Callback으로부터 받아온 값을 Viewmodel로 전달
        thisViewModel.getFontSetting(fontSetting)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        diaryOnBackPressed.remove()
    }
}
