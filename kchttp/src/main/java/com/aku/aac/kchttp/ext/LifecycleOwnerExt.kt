package com.aku.aac.kchttp.ext

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.aku.aac.kchttp.data.BaseResult
import kotlinx.coroutines.*

/**
 * @param block 调用Api请求数据的[suspend]方法
 *
 *
 * 在LifecycleOwner中绑定生命周期，执行完在Ui线程执行结果
 * 在[androidx.appcompat.app.AppCompatActivity]或[androidx.fragment.app.Fragment]中直接使用
 * 不过最好还是不使用此方法，而是使用[androidx.lifecycle.ViewModel]
 */
@ExperimentalCoroutinesApi
fun <T> LifecycleOwner.resultUi(
    block: suspend () -> BaseResult<T>,
    uiAction: BaseResult<T>.() -> Unit
) {
    val deferred = GlobalScope.async {
        val result = catchResult(block)
        withContext(Dispatchers.Main) {
            uiAction.invoke(result)
        }
    }
    val uiLifecycle = object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun cancel() {
            deferred.cancel()
        }
    }
    deferred.invokeOnCompletion {
        lifecycle.removeObserver(uiLifecycle)
    }
    lifecycle.addObserver(uiLifecycle)
    deferred.start()
}
