package com.aku.aac.kchttp.ext

import com.aku.aac.kchttp.KcHttp
import com.aku.aac.kchttp.R
import com.aku.aac.kchttp.core.*
import com.aku.aac.kchttp.data.BaseResult
import com.aku.aac.kchttp.data.ResultApi
import com.aku.aac.kchttp.data.ResultError
import com.aku.aac.kchttp.util.KcUtils

/*********** 数据检测 *********/

fun <T> BaseResult<T>.checkResult(): BaseResult<T> = KcHttpConfig.apiHandler.checkResult(this)

fun <T> Throwable.toBaseResult(): BaseResult<T> = KcHttpConfig.apiHandler.handleError<T>(this)

fun <T> BaseResult<T>.handleResult(): BaseResult<T> = KcHttpConfig.apiHandler.handleResult(this)

val <T> BaseResult<T>.isCodeDisabled: Boolean
    get() = KcHttpConfig.apiHandler.isCodeDisabled(this)
val <T> BaseResult<T>.isDataDisabled: Boolean
    get() = KcHttpConfig.apiHandler.isDataDisabled(this)

/**
 * 获取直接可使用的数据或者抛出异常
 */
val <T> BaseResult<T>.valueOrThrow: T
    @Throws(KcHttpException::class)
    get() {
        checkResult()
        return data!!
    }
/**
 * 错误信息或者为空的错误信息
 */
val BaseResult<*>.msgOrDefault: String
    get() = if (msg.isNotEmpty()) {
        msg
    } else KcUtils.getString(R.string.kchttp_net_empty_msg)

/*********** 操作 *********/
/**
 * 异常捕获
 */
suspend fun <T> catchResult(block: suspend () -> BaseResult<T>): BaseResult<T> {
    return try {
        block.invoke().checkResult()
    } catch (e: Throwable) {
        e.toBaseResult()
    }
}


/**
 * @author Zsc
 * @date   2019/4/30
 * @desc
 */
fun <T> createErrorResult(code: Int, msg: String, state: Int = RState.ERROR): BaseResult<T> {
    return ResultApi<T>().also {
        it.state = state
        it.code = code
        it.msg = msg
    }
}

/**
 * 创建Http调用成功的结果
 */
fun <T> T.createKcHttpResult(): BaseResult<T> {
    return ResultApi<T>().also {
        it.code = 200
        it.data = this
    }
}

/**
 * 成功时的操作
 * 只获取数据，如果需要拿取code 等信息，使用[doSuccessDetail]
 */
fun <T> BaseResult<T>.doSuccess(r: (T) -> Unit): BaseResult<T> {
    return apply {
        if (state == RState.SUCCESS) {
            r(data!!)
        }
    }
}

/**
 * 成功时的操作
 * 获取数据详情，如果只获取数据不需要code使用[doSuccess]
 */
fun <T> BaseResult<T>.doSuccessDetail(r: (BaseResult<T>) -> Unit): BaseResult<T> {
    return apply {
        if (state == RState.SUCCESS) {
            r(this)
        }
    }
}

/**
 * 失败时的操作
 * 如果在请求取消和失败时都执行，则使用[doErrorOrCancel]
 */
fun <T> BaseResult<T>.doError(e: (ResultError) -> Unit): BaseResult<T> {
    return apply {
        if (state == RState.ERROR) {
            e(this)
        }
    }
}

/**
 * 请求取消时的操作
 */
fun <T> BaseResult<T>.doCancel(c: (ResultError) -> Unit): BaseResult<T> {
    return apply {
        if (state == RState.CANCEL) {
            c(this)
        }
    }
}

/**
 * 失败或取消时的操作
 */
fun <T> BaseResult<T>.doErrorOrCancel(e: (ResultError) -> Unit): BaseResult<T> {
    return apply {
        if (state == RState.ERROR ||
            state == RState.CANCEL
        ) {
            e(this)
        }
    }
}


