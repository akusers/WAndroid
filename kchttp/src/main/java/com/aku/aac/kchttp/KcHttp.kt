package com.aku.aac.kchttp

import com.aku.aac.kchttp.core.ApiHandler
import com.aku.aac.kchttp.core.KcHttpConfig
import com.aku.aac.kchttp.core.KcHttpConfig.globalBaseUrl
import com.aku.aac.kchttp.core.KcHttpConfig.okHttpClientBuilder
import com.aku.aac.kchttp.core.KcHttpConfig.retrofitBuilder
import com.aku.aac.kchttp.core.KcHttpConfig.retrofits
import retrofit2.Retrofit

/**
 * kotlin Coroutine for http
 */
object KcHttp : ApiHandler by KcHttpConfig.apiHandler {

    /**
     * 创建并缓存Api
     */
    inline fun <reified T> createApi(
        baseUrl: String = globalBaseUrl
    ): T {
        return when (val retrofit = retrofits[T::class.java.simpleName]) {
            null -> create(baseUrl)
                .apply {
                    retrofits[T::class.java.simpleName] = this
                }
            else -> retrofit
        }.create(T::class.java)

    }

    /**
     *  创建api单次使用
     */
    inline fun <reified T> createSingleApi(
        url: String = globalBaseUrl,
        retrofit: Retrofit = create(url)
    ): T {
        return retrofit
            .create(T::class.java)
    }


    fun create(baseUrl: String = globalBaseUrl): Retrofit {
        return retrofitBuilder
            .baseUrl(baseUrl)
            .client(okHttpClientBuilder.build())
            .build()
    }


}