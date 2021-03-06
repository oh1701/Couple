package com.project.myapplication.ui.travel.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.databinding.FragmentTravelDiaryBinding
import com.project.myapplication.model.font.FontBindSettingModel
import com.project.myapplication.ui.dialog.view.FontDialogFragment
import com.project.myapplication.ui.dialog.view.WarningDialogFragment
import com.project.myapplication.ui.travel.viewmodel.TravelDiaryViewModel
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
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
    private lateinit var fontCustomCallback:EventCustomCallback<FontBindSettingModel>
    private lateinit var imageRemoveCustomCallback:EventCustomCallback<Boolean>
    private var fontSettingModel:FontBindSettingModel? = null
    
    private val imageCustomCallbackEvent:(Boolean) -> Unit = { boolean ->
        if (boolean)// ????????????
            thisViewModel.viewPagerRemoveImage()
    }
    
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
        startActivityForResult() // oncreate ??????.
    }

    override fun initView() {
        binding.travelDiaryViewModel = thisViewModel
        binding.travelDiaryFragment = this
        thisViewModel.getMetrics(resources.displayMetrics)

        fontCustomCallback = EventCustomCallback(fontCustomEvent)
        fontCustomCallback.setChanged()
        imageRemoveCustomCallback = EventCustomCallback(imageCustomCallbackEvent)
        imageRemoveCustomCallback.setChanged()

        if (arguments?.getInt("markerID") == 9999999 || arguments?.getInt("markerID") == null) {
            thisViewModel.createDiarysetting(sharedActivityViewModel.myLocationLatLng.value) // ???????????? ?????? ???????????????.
            thisViewModel.markerViewPagerToastMessage = true
        }
        else{
            thisViewModel.getDiary(arguments?.getInt("markerID")!!)
            thisViewModel.markerViewPagerToastMessage = false
        }
    }

    override fun initObserve() {
        thisViewModel.toastLiveData.observe(viewLifecycleOwner, EventObserver{ event -> // ??????, ????????? ?????????
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
            val colorList:ArrayList<ColorStateList> = arrayListOf()

            v.fontColorStateList.value?.forEach {
                colorList.add(ColorStateList.valueOf(it))
            }

            fontSettingModel = FontBindSettingModel(v.fontletterSpacing.value, v.fontlineSpacing.value,
            v.fontTypedSizeValue.value, v.fontcolorHex.value, v.fontTypeFace.value, colorList)
        })

        thisViewModel.createMarkerEvent.observe(viewLifecycleOwner, EventObserver{
            sharedActivityViewModel.newCreateMarker(thisViewModel.createDiaryID.value!!)
            supportFragmentManager.popBackStack()
        })

        thisViewModel.diaryTouchBtnCheck.observe(viewLifecycleOwner, CustomObserver{ boolean ->
            thisViewModel.viewEnabledValue(boolean.not()) // ????????? ????????? Enabled false ???????????????.
            sharedActivityViewModel.getViewPagerBtnString(boolean) // boolean ?????? ?????? ???????????? ????????? ??????, ?????? ??????
        })

        thisViewModel.removeWarningDialog.observe(viewLifecycleOwner, EventObserver{
            if(it){
                if(!warningDialogFragment.isAdded)
                    warningDialogFragment.show(supportFragmentManager, "${arguments?.getInt("markerID")}")
            }
        })

        thisViewModel.fontTypefaceToString.observe(viewLifecycleOwner, {
            thisViewModel.getFontTypeface(TextTypeFace().stringToFont(it, requireContext()))
        })

        thisViewModel.diaryFontBtnCheck.observe(viewLifecycleOwner, EventObserver{
            FontDialogFragment.newInstance(fontCustomCallback, fontSettingModel)
                .show(supportFragmentManager, this.javaClass.simpleName)
        })
    }

    fun cameraOpen(){ // startForResultCamera ??? registerForActivityResult??? AppCompat ?????? ????????? ????????????
        if(thisViewModel.diaryEnabled.value == true) { // ???????????? ?????? ???????????? ??????
            val option = arrayOf<CharSequence>("???????????? ????????????", "???????????? ????????????")
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setItems(option) { dialog, item ->
                if (option[item] == "???????????? ????????????") {
                    cameraFileUri = photoClass.getImage()
                    startForResultCamera.launch(cameraFileUri)
                } else {
                    startForResultAlbum.launch(photoClass.albumMultipleIntent())
                }
            }
            dialog.show()
        }
    }

    fun removeImageWarningDialog(){
        if(!warningDialogFragment.isAdded)
            WarningDialogFragment.newInstance(imageRemoveCustomCallback).show(supportFragmentManager, "removeImage")
    }

    fun fullScreenViewPagerOpen(){
        supportFragmentManager
            .beginTransaction()
            .addToBackStack("Map")
            .add(R.id.fragment_layout, ViewPagerFullScreenImageFragment.newInstance(thisViewModel.imageViewPagerNumber.value!!, thisViewModel.diaryImageUri.value!!))
            .commit()
    }

    private fun startActivityForResult(){
        //?????? ????????? oncreate?????? ??????????????????.
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