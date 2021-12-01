package com.project.myapplication.bind

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.project.myapplication.model.DiaryTagModel
import com.project.myapplication.ui.recyclerview.RecyclerTag

object RecyclerViewBind {
    @JvmStatic
    @BindingAdapter("recyclerViewSet")
    fun RecyclerView.setRecycler(diaryTagModel: ArrayList<DiaryTagModel>){
        this.apply{
            layoutManager = FlexboxLayoutManager(
                this.rootView.context,
                FlexDirection.ROW,
                FlexWrap.WRAP
            ) //가로정렬, 꽉차면 다음칸으로 넘어가게 만듬.
            setHasFixedSize(true)
            adapter = RecyclerTag(diaryTagModel)
        }
    }
}