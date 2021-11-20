package com.project.myapplication.ui.dialog.view

import androidx.lifecycle.ViewModel
import com.project.myapplication.R
import com.project.myapplication.base.BaseDialogFragment
import com.project.myapplication.databinding.DialogFragmentFontBinding
import com.project.myapplication.ui.dialog.viewmodel.FontDialogViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class FontDialogFragment:BaseDialogFragment<DialogFragmentFontBinding, FontDialogViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_fragment_font
    override val thisViewModel: FontDialogViewModel by viewModel()

    override fun initView() {
        binding.fontCancel.setOnClickListener {
            this.dialog?.dismiss()
        }

    }

    override fun initObserve() {

    }

    override fun onResume() {
        super.onResume()

        dialogSettings.dialogMetricsSetting(this.dialog, 9, 9)
    }
}