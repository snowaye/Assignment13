package com.padc.batch9.assignment13.mvp.presenter

import androidx.lifecycle.ViewModel
import com.padc.batch9.assignment13.mvp.view.BaseView

/**
 * Created by Ye Pyae Sone Tun
 * on 2019-09-29.
 */

abstract class BasePresenter<T: BaseView> : ViewModel() {

    protected lateinit var mView: T

    open fun initPresenter(view: T){
        this.mView  = view
    }
}