package com.zsc.aac.kchttp

import com.zsc.aac.kchttp.core.BaseUrlKey
import com.zsc.aac.kchttp.core.KcHttpConfig.globalBaseUrl
import com.zsc.aac.kchttp.core.KcHttpConfig.okHttpClientBuilder
import com.zsc.aac.kchttp.core.KcHttpConfig.retrofitBuilder
import com.zsc.aac.kchttp.core.KcHttpConfig.retrofits
import com.zsc.aac.kchttp.core.KcHttpConfig.urlCache
import retrofit2.Retrofit

/**
 * kotlin Coroutine for http
 */
object KcHttp {
    /**
     * 创建并缓存Api
     */
    inline fun <reified T> createApi(
        baseUrl: String = globalBaseUrl
    ): T {
        return this.loadUrlKey<T>()
            .let {
                when (val retrofit = retrofits[it]) {
                    null -> create(urlCache[it] ?: baseUrl).apply { retrofits[it] = this }
                    else -> retrofit
                }.create(T::class.java)
            }
    }

    /**
     * 加载Service对应的Retrofit缓存的key值
     * 如果接口有[BaseUrlKey]注解,则取key对应的值，否则取类名全称
     */
    inline fun <reified T> loadUrlKey(): String {
        return T::class.java.run {
            if (isAnnotationPresent(BaseUrlKey::class.java))
                getAnnotation(BaseUrlKey::class.java)?.value ?: simpleName
            else simpleName
        }
    }

    /**
     *  创建api单次使用
     */
    inline fun <reified T> createSingleApi(
        url: String = globalBaseUrl,
        retrofit: Retrofit = create(url)
    ): T {
        return retrofit.create(T::class.java)
    }

    /**
     * 根据已有的okHttpClientBuilder创建 retrofit实例
     */
    fun create(baseUrl: String = globalBaseUrl): Retrofit {
        return retrofitBuilder
            .baseUrl(baseUrl)
            .client(okHttpClientBuilder.build())
            .build()
    }


}