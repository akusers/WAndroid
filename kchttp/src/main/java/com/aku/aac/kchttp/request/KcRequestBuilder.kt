package com.aku.aac.kchttp.request

import androidx.lifecycle.LifecycleOwner
import com.aku.aac.kchttp.data.BaseResult

interface KcRequestBuilder<T> {
    val owner: LifecycleOwner

    val data:suspend () -> BaseResult<T>

}