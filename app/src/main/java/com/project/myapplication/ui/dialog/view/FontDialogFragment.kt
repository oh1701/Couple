package com.project.myapplication.ui.dialog.view

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.myapplication.R
import com.project.myapplication.base.BaseDialogFragment
import com.project.myapplication.databinding.DialogFragmentFontBinding
import com.project.myapplication.model.font.FontBindSettingModel
import com.project.myapplication.ui.dialog.viewmodel.FontDialogViewModel
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import com.project.myapplication.ui.travel.viewpager.ViewPagerDiaryImageFragment
import com.project.myapplication.utils.EventCustomCallback
import com.project.myapplication.utils.customobserver.EventObserver
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class FontDialogFragment:BaseDialogFragment<DialogFragmentFontBinding, FontDialogViewModel>(){
    override val layoutResourceId: Int
        get() = R.layout.dialog_fragment_font
    override val thisViewModel: FontDialogViewModel by viewModel()

    override fun initView() {
        binding.fontDialogViewModel = thisViewModel
        binding.fontDialogFragment = this
        binding.fontRecyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.fontRecyclerview

        thisViewModel.getMetrics(resources.displayMetrics)
        thisViewModel.getFontSetting(arguments?.getParcelable(fontSetting))
    }

    override fun initObserve() {
        thisViewModel.toastLiveData.observe(this, EventObserver{message ->
            toast(message)
        })

        thisViewModel.fonSettingComplete.observe(this, EventObserver{ // Complete 되면 Callback 처리하기
            arguments?.getParcelable<EventCustomCallback>(listener)?.dataChangeListener?.fontSettingCallback(
                FontBindSettingModel(thisViewModel.letterSpacing.value,
                    thisViewModel.lineSpacing.value,
                    thisViewModel.textTypedValue.value,
                    binding.previewedit.textColors,
                    binding.previewedit.typeface)
            )
            this.dialog?.dismiss()
        })
    }


    override fun onResume() {
        super.onResume()

        dialogSettings.dialogMetricsSetting(this.dialog, 9, 9)
    }

    companion object{
        private const val listener = "listener"
        private const val fontSetting = "FontSetting"

        fun newInstance(customCallback: EventCustomCallback, fontSettingModel:FontBindSettingModel?): FontDialogFragment {
            val f = FontDialogFragment()

            val args = Bundle()
            args.putParcelable(listener, customCallback)
            args.putParcelable(fontSetting, fontSettingModel)

            f.arguments = args

            return f
        }
    }
}