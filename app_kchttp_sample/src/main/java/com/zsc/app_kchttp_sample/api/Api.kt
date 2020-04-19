package com.zsc.app_kchttp_sample.api

import com.zsc.aac.kchttp.KcHttp
import com.zsc.aac.kchttp.core.ApiHandler
import com.zsc.aac.kchttp.core.KcHttpConfig
import com.zsc.aac.kchttp.data.BaseResult
import okhttp3.logging.HttpLoggingInterceptor

val api by lazy {
    KcHttpConfig.apiHandler = object : ApiHandler {
        override fun <T> isCodeDisabled(resultApi: BaseResult<T>): Boolean {
            return resultApi.code != 0 && super.isCodeDisabled(resultApi)
        }
    }
    KcHttpConfig.okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    KcHttp.createApi<WanApi>(WanApi.BASE_URL)
}