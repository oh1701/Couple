package com.project.myapplication.bind

import android.util.Log
import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.project.myapplication.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object ImageButtonBind {
    @JvmStatic
    @BindingAdapter("OnOffBoolean")
    fun ImageButton.onOffBoolean(check:Boolean?){
        Observable.timer(200L, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                if (check == true) {
                    this.setBackgroundResource(R.drawable.diary_btn_on)
                } else {
                    this.setBackgroundResource(R.drawable.btn_select)
                }
            }
            .subscribe()
    }


    @JvmStatic
    @BindingAdapter("MapCameraAutoCheck")
    fun ImageButton.mapCameraAutoCheck(check:Boolean?){
        if(check == true)
            Glide.with(this.context).load(R.drawable.ic_baseline_location_searching_24).into(this)
        else
            Glide.with(this.context).load(R.drawable.ic_baseline_location_disabled_24).into(this)

    }
}