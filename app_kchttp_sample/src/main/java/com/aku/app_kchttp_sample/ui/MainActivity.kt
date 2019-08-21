package com.aku.app_kchttp_sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aku.aac.kchttp.KcHttp
import com.aku.aac.kchttp.core.ApiHandler
import com.aku.aac.kchttp.core.KcHttpConfig
import com.aku.aac.kchttp.data.BaseResult
import com.aku.aac.kchttp.ext.resultUi
import com.aku.app_kchttp_sample.R
import com.aku.app_kchttp_sample.api.WanApi
import com.aku.common.util.logD
import com.aku.common.util.toJson
import kotlinx.android.synthetic.main.main_act.*
import okhttp3.logging.HttpLoggingInterceptor

class MainActivity : AppCompatActivity() {

    private val api by lazy {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act)
        btnAct.setOnClickListener {
            resultUi({
                api.getBanner()
            }) {
                logD(this)
                tvContent.text = toJson()
            }
        }
    }
}