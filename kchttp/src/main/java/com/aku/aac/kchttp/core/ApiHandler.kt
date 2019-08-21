package com.aku.aac.kchttp.core

import com.aku.aac.kchttp.R
import com.aku.aac.kchttp.data.BaseResult
import com.aku.aac.kchttp.ext.createErrorResult
import com.aku.aac.kchttp.ext.msgOrDefault
import com.aku.aac.kchttp.util.KcUtils
import com.google.gson.JsonParseException
import kotlinx.coroutines.CancellationException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.text.ParseException

/**
 * @author Zsc
 * @date   2019/5/19
 * @desc
 */
interface ApiHandler {

    /**
     * 检测code是否可用
     */
    fun <T> isCodeDisabled(resultApi: BaseResult<T>): Boolean {
        return when (resultApi.code) {
            200, 10000 -> false
            else -> true
        }
    }

    /**
     * 检测返回结果是否可用
     */
    fun <T> isDataDisabled(resultApi: BaseResult<T>): Boolean {
        return resultApi.data == null
    }

    /**
     * 检测数据并在数据不可用时抛异常
     */
    @Throws(KcHttpException::class)
    fun <T> checkResult(resultApi: BaseResult<T>): BaseResult<T> {
        if (isCodeDisabled(resultApi)) {
            throw KcHttpException(resultApi.code, resultApi.msgOrDefault)
        }
        if (isDataDisabled(resultApi)) {
            throw KcHttpException(
                ApiErrorCode.RESULT_IS_NULL
                , KcUtils.getString(R.string.kchttp_net_empty_result)
            )
        }
        return resultApi
    }

    /**
     * 默认的检测返回的数据
     */
    fun <T> handleResult(resultApi: BaseResult<T>): BaseResult<T> {
        return resultApi.apply {
            if (isCodeDisabled(resultApi)) {
                resultApi.state = RState.ERROR
                resultApi.msg = resultApi.msgOrDefault
            } else if (isDataDisabled(resultApi)) {
                resultApi.state = RState.ERROR
                resultApi.msg = KcUtils.getString(R.string.kchttp_net_empty_result)
            }
        }
    }

    /**
     * 默认异常拦截处理
     * @param e 返回的异常
     * @return  由异常构建结果[BaseResult]
     */
    fun <T> handleError(e: Throwable): BaseResult<T> {
        return when (e) {
            is HttpException -> {
                when (val code = e.code()) {
                    504 -> createErrorResult(
                        code,
                        KcUtils.getString(R.string.kchttp_net_504)
                    )
                    in 400 until 500 -> createErrorResult(
                        code,
                        KcUtils.getString(R.string.kchttp_net_4xx)
                    )
                    in 500 until 600 -> createErrorResult(
                        code,
                        KcUtils.getString(R.string.kchttp_net_5xx)
                    )
                    else -> createErrorResult(
                        ApiErrorCode.ERROR,
                        KcUtils.getString(R.string.kchttp_net_unknown_error)
                    )
                }
            }
            is KcHttpException -> createErrorResult(e.code, e.message)
            is CancellationException -> createErrorResult(
                ApiErrorCode.CANCEL,
                KcUtils.getString(R.string.kchttp_net_cancel),
                RState.CANCEL
            )
            is SocketTimeoutException -> createErrorResult(
                ApiErrorCode.TIME_OUT,
                KcUtils.getString(R.string.kchttp_net_504)
            )
            is JsonParseException,
            is JSONException,
            is ParseException -> createErrorResult(
                ApiErrorCode.PARSE_ERROR,
                KcUtils.getString(R.string.kchttp_net_504)
            )
            is ConnectException -> createErrorResult(
                ApiErrorCode.CONNECT_ERROR,
                KcUtils.getString(R.string.kchttp_net_504)
            )
            else -> createErrorResult(
                ApiErrorCode.UNKNOWN,
                KcUtils.getString(R.string.kchttp_net_unknown_error)
            )
        }
    }
}