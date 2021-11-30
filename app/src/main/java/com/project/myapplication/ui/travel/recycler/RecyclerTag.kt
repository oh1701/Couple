package com.project.myapplication.ui.travel.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.project.myapplication.R
import com.project.myapplication.model.DiaryTagModel

class RecyclerTag(private val tagline:ArrayList<DiaryTagModel>): RecyclerView.Adapter<RecyclerTag.ViewHolder>(){
    lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerTag.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_tag_layout, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerTag.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return tagline.size
    }

    class ViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
    }
}