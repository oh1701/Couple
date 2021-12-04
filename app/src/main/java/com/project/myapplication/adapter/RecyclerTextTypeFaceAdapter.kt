package com.project.myapplication.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.project.myapplication.R
import com.project.myapplication.model.font.FontTypeFace

class RecyclerTextTypeFaceAdapter(private val fontlist:ArrayList<FontTypeFace>, private val fontText:EditText):RecyclerView.Adapter<RecyclerTextTypeFaceAdapter.ViewHolder>() {
    private var btn: Array<Button?> = arrayOfNulls(15)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_font_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int { // 포지션으로 리턴 안하면 빠르게 스크롤 시 View 꼬이는 버그가 생긴다.
        return position
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fontBtn.text = fontlist[position].name
        holder.fontBtn.typeface = fontlist[position].font

        if(btn[position] == null) {
            btn[position] = holder.fontBtn
        }

        if (fontlist[position].font == fontText.typeface) {
            btn[position]?.setBackgroundResource(R.drawable.font_on) // 선택한 것은 font_on으로 바꾸기.
        } else {
            btn[position]?.setBackgroundColor(Color.parseColor("#ECE1E1")) // 나머지는 일반 배경색으로 지정.
        }

        holder.fontBtn.setOnClickListener {
            for(i in btn.indices) {
                if(i == position) {
                    btn[i]?.setBackgroundResource(R.drawable.font_on) // 선택한 것은 font_on으로 바꾸기.
                    fontText.typeface = holder.fontBtn.typeface //선택한 typeface로 에딧텍스트 바꾸기.
                }
                else{
                    btn[i]?.setBackgroundColor(Color.parseColor("#ECE1E1")) // 나머지는 일반 배경색으로 지정.
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return fontlist.size
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val fontBtn: Button = view.findViewById<Button>(R.id.font_recycler_btn)
    }
}