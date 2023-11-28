package com.my.myhisab.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.my.myhisab.App
import com.my.utils.ProgressView

abstract class BaseViewModel : ViewModel() {


    val loader by lazy {
        ProgressView.getLoader(App.appContext!!)
    }

    val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

}