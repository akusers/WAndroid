package com.aku.aac.kchttp.core

import com.aku.aac.kchttp.KcHttp
import com.aku.aac.kchttp.data.BaseResult

fun <T> KcHttp.checkResult(resultApi: BaseResult<T>): BaseResult<T> {
    return KcHttpConfig.apiHandler.checkResult(resultApi)
}

fun <T> KcHttp.handleError(e: Throwable): BaseResult<T> {
    return KcHttpConfig.apiHandler.handleError(e)
}

fun <T> KcHttp.handleResult(resultApi: BaseResult<T>): BaseResult<T> {
    return KcHttpConfig.apiHandler.handleResult(resultApi)
}

fun <T> KcHttp.isCodeDisabled(resultApi: BaseResult<T>): Boolean {
    return KcHttpConfig.apiHandler.isCodeDisabled(resultApi)
}

fun <T> KcHttp.isDataDisabled(resultApi: BaseResult<T>): Boolean {
    return KcHttpConfig.apiHandler.isDataDisabled(resultApi)
}
    