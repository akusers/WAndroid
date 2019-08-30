package com.aku.aac.kchttp.request

import android.app.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.aku.aac.kchttp.data.BaseResult
import com.aku.aac.kchttp.data.ResultError
import com.aku.aac.kchttp.ext.*
import kotlinx.coroutines.*

class KcRequest<T>(private val owner: LifecycleOwner) {

    //加载数据的异步请求
    private lateinit var data: suspend () -> BaseResult<T>
    //加载开始的操作
    private var start: (() -> Unit)? = null
    //加载成功的操作
    private var success: (BaseResult<T>.() -> Unit)? = null
    //加载失败的操作
    private var error: (ResultError.() -> Unit)? = null
    //加载取消的操作
    private var cancel: (ResultError.() -> Unit)? = null
    //加载完成的操作
    private var complete: (() -> Unit)? = null

    //加载时的弹窗
    var dialog: Dialog? = null

    fun bindDialog(dialogBuild: () -> Dialog) {
        dialog = dialogBuild.invoke()
    }

    fun loadData(load: suspend () -> BaseResult<T>) {
        data = load
    }

    fun onStart(action: () -> Unit) {
        start = action
    }

    fun onComplete(action: () -> Unit) {
        complete = action
    }

    fun onSuccess(action: BaseResult<T>.() -> Unit) {
        success = action
    }

    fun onError(action: ResultError.() -> Unit) {
        error = action
    }

    fun onCancel(action: ResultError.() -> Unit) {
        cancel = action
    }

    private fun BaseResult<T>.doAll() {
        doSuccess { success?.invoke(this) }
        doError { error?.invoke(this) }
        doCancel { cancel?.invoke(this) }
    }

    val job: Job by lazy {
        GlobalScope.async(Dispatchers.IO) {
            launch(Dispatchers.Main) {
                start?.invoke()
            }
            val result0 = catchResult(data)
            launch(Dispatchers.Main) {
                result0.doAll()
            }
        }
    }

    fun request() {
        val uiLifecycle = object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun cancel() {
                job.cancel()
            }
        }
        job.invokeOnCompletion {
            it?.run {
                toBaseResult<T>().doAll()
            }
            complete?.invoke()
            owner.lifecycle.removeObserver(uiLifecycle)
        }
        owner.lifecycle.addObserver(uiLifecycle)
        job.start()
    }

}