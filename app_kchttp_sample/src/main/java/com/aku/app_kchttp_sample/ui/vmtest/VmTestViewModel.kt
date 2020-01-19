package com.aku.app_kchttp_sample.ui.vmtest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aku.aac.kchttp.ext.catchResult
import com.aku.aac.kchttp.ext.doError
import com.aku.aac.kchttp.ext.doSuccess
import com.aku.app_kchttp_sample.api.api
import com.aku.app_kchttp_sample.data.Banner
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class VmTestViewModel : ViewModel() {

    val listBanner = MutableLiveData<List<Banner>>()

    fun loadBanner():Job {
       return viewModelScope.launch(Dispatchers.IO) {
            catchResult { api.getBanner() }
                .doSuccess {
                    listBanner.postValue(it)
                }.doError {
                    ToastUtils.showShort(it.msg)
                }
        }


    }


}