package com.aku.app_kchttp_sample

import com.aku.aac.kchttp.KcHttp
import com.aku.aac.kchttp.core.KcHttpConfig
import com.aku.app_kchttp_sample.api.WanApi
import okhttp3.logging.HttpLoggingInterceptor

val api by lazy {
    KcHttpConfig.okHttpClientBuilder
        .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            println(it)
        }).apply { level = HttpLoggingInterceptor.Level.BODY })
    KcHttp.createApi<WanApi>(WanApi.BASE_URL)
}