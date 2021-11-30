package com.project.myapplication.ui.dialog.view

import com.project.myapplication.R
import com.project.myapplication.base.BaseDialogFragment
import com.project.myapplication.databinding.DialogFragmentFontBinding
import com.project.myapplication.model.font.FontBindSettingModel
import com.project.myapplication.ui.dialog.viewmodel.FontDialogViewModel
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import com.project.myapplication.utils.EventCustomCallback
import com.project.myapplication.utils.customobserver.EventObserver
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class FontDialogFragment:BaseDialogFragment<DialogFragmentFontBinding, FontDialogViewModel>(){
    override val layoutResourceId: Int
        get() = R.layout.dialog_fragment_font
    override val thisViewModel: FontDialogViewModel by viewModel()
    private val sharedActivityViewModel:TravelViewModel by sharedViewModel()

    override fun initView() {
        binding.fontDialogViewModel = thisViewModel
        binding.fontDialogFragment = this

        thisViewModel.getMetrics(resources.displayMetrics)
        thisViewModel.getFontSetting(arguments?.getParcelable<FontBindSettingModel>("FontSetting"))
    }

    override fun initObserve() {
        thisViewModel.toastLiveData.observe(this, EventObserver{message ->
            toast(message)
        })

        thisViewModel.fonSettingComplete.observe(this, EventObserver{ // Complete 되면 Callback 처리하기
            arguments?.getParcelable<EventCustomCallback>("listener")?.dataChangeListener?.fontSettingCallback(
                FontBindSettingModel(thisViewModel.letterSpacing.value,
                    thisViewModel.lineSpacing.value,
                    thisViewModel.textTypedValue.value,
                    binding.previewedit.textColors)
            )

            this.dialog?.dismiss()
        })
    }


    override fun onResume() {
        super.onResume()

        dialogSettings.dialogMetricsSetting(this.dialog, 9, 9)
    }
}