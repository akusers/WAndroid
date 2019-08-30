package com.aku.app_kchttp_sample.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.aku.aac.kchttp.KcHttp
import com.aku.aac.kchttp.core.ApiHandler
import com.aku.aac.kchttp.core.KcHttpConfig
import com.aku.aac.kchttp.data.BaseResult
import com.aku.aac.kchttp.ext.requestEasy
import com.aku.aac.kchttp.ext.resultUi
import com.aku.app_kchttp_sample.R
import com.aku.app_kchttp_sample.api.WanApi
import com.aku.app_kchttp_sample.data.Banner
import com.aku.common.util.bindDialog
import com.aku.common.util.toJson
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.main_act.*
import kotlinx.coroutines.delay
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
                tvContent.text = toJson()
            }
        }
        btnTest.setOnClickListener {
            requestEasy<List<Banner>> {
                loadData {
                    api.getBanner()
                }
                onSuccess {
                    tvContent.text = "Easy:${toJson()}"
                    LogUtils.d("Success:${toJson()}")
                }
                onError {
                    LogUtils.d("Error:${toJson()}")
                }
            }
        }
        btnTestError.setOnClickListener {
            requestEasy<List<Banner>> {
                //绑定的dialog，如果用progress效果更好，不过最好是直接使用DialogFragment来加载请求
                val dialog by lazy {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("请求中")
                        .setNegativeButton("取消", null)
                        .show()
                }
                loadData {
                    //延迟1.5s 避免加载太快看不到效果
                    delay(1500)
                    api.getBanner()
                }
                onStart {
                    job.bindDialog(dialog)
                    LogUtils.d("onStart")
                }
                onComplete {
                    LogUtils.d("onComplete")
                }
                onCancel { tvContent.text = "onCancel:${toJson()}" }
                onSuccess {
                    tvContent.text = "Success:${toJson()}"
                    LogUtils.d("doSuccess:${toJson()}")
                }
                onError {
                    LogUtils.d("Error:${toJson()}")
                    tvContent.text = "Error:${toJson()}"
                }
            }
        }

    }
}