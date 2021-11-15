package com.project.myapplication.ui.dialog.view

import com.project.myapplication.R
import com.project.myapplication.base.BaseDialogFragment
import com.project.myapplication.common.CommonIntent
import com.project.myapplication.databinding.DialogFragmentWarningBinding
import com.project.myapplication.ui.dialog.viewmodel.WarningDialogViewModel
import org.koin.android.ext.android.inject
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
        thisViewModel.selectButtonClick.observe(this){
            it.getContentIfNotHandled()?.let{ event ->
                when(event){
                    "onBackPressed" -> requireActivity().onBackPressed()
                    "dismiss" -> dialog?.dismiss()
                    "Setting" -> {
                        commonIntent.dialogIntentSetting()
                        dialog?.dismiss()
                    }
                    "save" -> { thisViewModel.saveContent() }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        dialogMetricsSetting()
    }

    private fun dialogMetricsSetting(){
        val lp = dialog?.window?.attributes
        lp?.width = metrics.widthPixels * 7 / 10 //레이아웃 params 에 width, height 넣어주기.
        lp?.height = metrics.heightPixels * 5 / 10
        dialog?.window?.attributes = lp // 다이얼로그 표출 넓이 넣어주기.
    }
}

