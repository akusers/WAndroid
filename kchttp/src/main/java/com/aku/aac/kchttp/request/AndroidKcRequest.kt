package com.aku.aac.kchttp.request

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.aku.aac.kchttp.data.BaseResult
import com.aku.aac.kchttp.data.ResultError
import com.aku.aac.kchttp.ext.*
import kotlinx.coroutines.*

class AndroidKcRequest<T>(override val owner: LifecycleOwner) : KcRequestBuilder<T> {

    override lateinit var data: suspend () -> BaseResult<T>
    var success: (BaseResult<T>.() -> Unit)? = null
    var error: (ResultError.() -> Unit)? = null
    var cancel: (ResultError.() -> Unit)? = null

    var start: (() -> Unit)? = null
    var complete: (() -> Unit)? = null

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