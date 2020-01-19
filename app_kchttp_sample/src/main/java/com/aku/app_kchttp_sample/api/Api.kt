package com.aku.app_kchttp_sample.api

import com.aku.aac.kchttp.KcHttp
import com.aku.aac.kchttp.core.ApiHandler
import com.aku.aac.kchttp.core.KcHttpConfig
import com.aku.aac.kchttp.data.BaseResult
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