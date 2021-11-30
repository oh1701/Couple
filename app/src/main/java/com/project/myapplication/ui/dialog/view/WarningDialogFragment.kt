package com.project.myapplication.ui.dialog.view

import com.project.myapplication.R
import com.project.myapplication.base.BaseDialogFragment
import com.project.myapplication.databinding.DialogFragmentWarningBinding
import com.project.myapplication.ui.dialog.viewmodel.WarningDialogViewModel
import com.project.myapplication.utils.customobserver.EventObserver
import org.koin.android.viewmodel.ext.android.viewModel

class WarningDialogFragment:BaseDialogFragment<DialogFragmentWarningBinding, WarningDialogViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_fragment_warning
    override val thisViewModel: WarningDialogViewModel by viewModel()

    override fun initView() {
        binding.dialogViewModel = thisViewModel

        thisViewModel.settingDialog(this.tag)
    }

    override fun initObserve() {
        thisViewModel.selectButtonClick.observe(this, EventObserver{ event ->
            when(event){
                "onBackPressed" -> requireActivity().onBackPressed()
                "dismiss" -> dialog?.dismiss()
                "Setting" -> {
                    commonIntent.dialogIntentSetting()
                    dialog?.dismiss()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        dialogSettings.dialogMetricsSetting(this.dialog, 7, 5)
    }
}

