package com.project.myapplication.ui.start.view

import com.project.myapplication.R
import com.project.myapplication.base.BaseFragment
import com.project.myapplication.base.BaseViewModel
import com.project.myapplication.databinding.DialogSelectPictureAlbumBinding
import org.koin.android.viewmodel.ext.android.viewModel

class SetCoupleFragment:BaseFragment<DialogSelectPictureAlbumBinding, BaseViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_select_picture_album
    override val thisViewModel: BaseViewModel by viewModel()

    override fun initView() {

    }

    override fun initObserve() {
    }
}