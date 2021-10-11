package com.project.myapplication.intro.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.myapplication.R

class BasicIntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_intro)
    }
}