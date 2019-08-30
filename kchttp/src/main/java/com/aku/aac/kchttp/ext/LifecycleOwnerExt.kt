package com.aku.aac.kchttp.ext

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.aku.aac.kchttp.data.BaseResult
import com.aku.aac.kchttp.request.KcRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 *
 * 构造[KcRequest]来请求数据
 * 在[androidx.appcompat.app.AppCompatActivity]或[androidx.fragment.app.Fragment]中直接使用
 * 不过最好还是不使用此方法，而是使用[androidx.lifecycle.ViewModel]
 */
fun <T> LifecycleOwner.requestEasy(init: KcRequest<T>.() -> Unit): KcRequest<T> {
    return KcRequest<T>(this).apply {
        init()
        request()
    }
}

