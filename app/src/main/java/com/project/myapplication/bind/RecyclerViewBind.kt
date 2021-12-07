package com.project.myapplication.bind

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.myapplication.adapter.recycler_adpater.RecyclerTextTypeFaceAdapter
import com.project.myapplication.model.font.FontTypeFace
import com.project.myapplication.utils.TextTypeFace

object RecyclerViewBind {
//    @JvmStatic
//    @BindingAdapter("recyclerTagSet")
//    fun RecyclerView.recyclerTagSet(diaryTagModel: ArrayList<DiaryTagModel>){
//        this.apply{
//            layoutManager = FlexboxLayoutManager(
//                this.rootView.context,
//                FlexDirection.ROW,
//                FlexWrap.WRAP
//            ) //가로정렬, 꽉차면 다음칸으로 넘어가게 만듬.
//            setHasFixedSize(true)
//            adapter = RecyclerTagAdapter(diaryTagModel)
//        }
//    }

    @JvmStatic
    @BindingAdapter("recyclerFont")
    fun RecyclerView.recyclerTypeFaceSet(text:EditText){
        val fontArray: ArrayList<FontTypeFace> = TextTypeFace().returnTypefaceArray(this.rootView.context)
        this.apply{
            layoutManager = GridLayoutManager(
                this.rootView.context,
                3,
                GridLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(true)
            adapter = RecyclerTextTypeFaceAdapter(fontArray, text)
        }
    }
}