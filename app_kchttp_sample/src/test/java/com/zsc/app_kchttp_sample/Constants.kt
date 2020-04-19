package com.zsc.app_kchttp_sample

import com.zsc.aac.kchttp.KcHttp
import com.zsc.aac.kchttp.core.KcHttpConfig
import com.zsc.app_kchttp_sample.api.WanApi
import okhttp3.logging.HttpLoggingInterceptor

val api by lazy {
    KcHttpConfig.okHttpClientBuilder
        .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            println(it)
        }).apply { level = HttpLoggingInterceptor.Level.BODY })
    KcHttp.createApi<WanApi>(WanApi.BASE_URL)
}