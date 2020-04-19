package com.zsc.aac.kchttp.core

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Zsc
 * @date   2019/8/7
 * @desc KcHttp基础配置
 */
object KcHttpConfig {

    var apiHandler = object : ApiHandler {}

    var globalBaseUrl: String = "http://www.aku.com/"

    /**
     * 全局的retrofitBuilder
     */
    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            //使用Gson
            .addConverterFactory(GsonConverterFactory.create())
    }
    /**
     * 全局的 okHttpClientBuilder
     */
    val okHttpClientBuilder: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder()
    }
    /**
     * 缓存retrofit重复使用
     */
    val retrofits: MutableMap<String, Retrofit> = mutableMapOf()

}