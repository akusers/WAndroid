package com.zsc.app_kchttp_sample.ui.vmtest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zsc.aac.kchttp.data.BaseResult
import com.zsc.aac.kchttp.ext.catchResult
import com.zsc.app_kchttp_sample.api.api
import com.zsc.app_kchttp_sample.data.Banner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class VmTestViewModel : ViewModel() {

    val listBanner = MutableLiveData<BaseResult<List<Banner>>>()

    fun loadBanner():Job {
       return viewModelScope.launch(Dispatchers.IO) {
           listBanner.postValue(catchResult { api.getBanner() })
        }
    }


}