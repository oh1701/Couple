package com.project.myapplication.ui.dialog.view

import com.project.myapplication.R
import com.project.myapplication.base.BaseDialogFragment
import com.project.myapplication.databinding.DialogFragmentWarningBinding
import com.project.myapplication.ui.dialog.viewmodel.WarningDialogViewModel
import com.project.myapplication.ui.travel.view.TravelActivity
import com.project.myapplication.ui.travel.viewmodel.TravelViewModel
import com.project.myapplication.utils.customobserver.EventObserver
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class WarningDialogFragment:BaseDialogFragment<DialogFragmentWarningBinding, WarningDialogViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_fragment_warning
    override val thisViewModel: WarningDialogViewModel by viewModel()
    private val sharedActivityViewModel:TravelViewModel by sharedViewModel()

    override fun initView() {
        binding.dialogViewModel = thisViewModel

        thisViewModel.settingDialog(this.tag)
    }

    override fun initObserve() {
        thisViewModel.selectButtonClick.observe(viewLifecycleOwner, EventObserver{ event ->
            when(event){
                "onBackPressed" -> requireActivity().onBackPressed()
                "dismiss" -> dialog?.dismiss()
                "Setting" -> {
                    commonIntent.dialogIntentSetting()
                    dialog?.dismiss()
                }
                "removeDiary" -> { // remove ID는 태그로 지정해줬음.
                    thisViewModel.removeDiary(this.tag?.toInt()!!)
                    dialog?.dismiss()
                    requireActivity().onBackPressed()
                }
            }
        })

        thisViewModel.markerRemoveShared.observe(viewLifecycleOwner, EventObserver{
            sharedActivityViewModel.removeMarker(it)
        })
    }



    override fun onResume() {
        super.onResume()

        dialogSettings.dialogMetricsSetting(this.dialog, 7, 5)
    }
}

