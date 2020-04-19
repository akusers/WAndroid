package com.zsc.app_kchttp_sample.data

import com.zsc.aac.kchttp.core.RState
import com.zsc.aac.kchttp.data.BaseResult
import com.google.gson.annotations.SerializedName

class HttpResponse<T> : BaseResult<T> {

    @SerializedName("errorCode")
    override var code: Int = 0

    @SerializedName("errorMsg")
    override var msg: String = ""

    override var data: T? = null
    /**
     * 请求返回的状态[RState]
     */
    override var state: Int = RState.SUCCESS

}