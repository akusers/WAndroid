package com.aku.aac.kchttp.ext

import com.aku.aac.kchttp.KcHttp
import com.aku.aac.kchttp.R
import com.aku.aac.kchttp.core.ApiErrorCode
import com.aku.aac.kchttp.core.checkResult
import com.aku.aac.kchttp.core.handleError
import com.aku.aac.kchttp.data.BaseResult
import com.aku.aac.kchttp.util.KcUtils
import kotlinx.coroutines.Deferred

/**
 * @author Zsc
 * @date   2019/4/30
 * @desc
 */
suspend fun <T> Deferred<BaseResult<T>>.awaitResult(): BaseResult<T> {
    return try {
        KcHttp.checkResult(await())
    } catch (e: Exception) {
        return KcHttp.handleError(e)
    }
}

suspend fun <T> Deferred<BaseResult<T>>.awaitWithNetCheck(): BaseResult<T> {
    return if (KcUtils.isConnected()) {
        awaitResult()
    } else createErrorResult(
        ApiErrorCode.NO_NET_WORK,
        KcUtils.getString(R.string.kchttp_no_net_work)
    )
}