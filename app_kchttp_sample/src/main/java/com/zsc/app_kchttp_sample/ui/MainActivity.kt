package com.zsc.app_kchttp_sample.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.zsc.aac.kchttp.ext.requestEasy
import com.zsc.app_kchttp_sample.R
import com.zsc.app_kchttp_sample.api.api
import com.zsc.app_kchttp_sample.data.Banner
import com.zsc.app_kchttp_sample.ui.vmtest.VmTestActivity
import com.zsc.common.util.toJson
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.main_act.*
import kotlinx.android.synthetic.main.progress_dialog.*
import kotlinx.android.synthetic.main.progress_dialog.view.*
import kotlinx.coroutines.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act)

        btnTest.setOnClickListener {
            requestEasy<List<Banner>> {
                loadData { api.getBanner() }
                onSuccess { tvContent.text = "Easy:${toJson()}" }
                onError { tvContent.text = "Easy:${toJson()}" }
            }
        }
        btnTestCancel.setOnClickListener {
            requestEasy<List<Banner>> {
                //最好是直接使用DialogFragment来加载请求
                bindDialog { getCancelDialog() }
                loadData {
                    //延迟1.5s 避免加载太快看不到效果,模拟连续加载
                    delay(500)
                    withContext(Dispatchers.Main) {
                        dialog!!.tvMsg.text = "loading......1"
                    }
                    delay(500)
                    withContext(Dispatchers.Main) {
                        dialog!!.tvMsg.text = "loading......2"
                    }
                    delay(500)
                    withContext(Dispatchers.Main) {
                        dialog!!.tvMsg.text = "loading......3"
                    }
                    api.getBanner()
                }
                onStart {
                    dialog?.run {
                        GlobalScope.launch(Dispatchers.Main) {
                            //延迟一段时间再设置取消请求按钮可用
                            delay(600)
                            if (isShowing) {
                                btnCancel.isEnabled = true
                            }
                        }
                    }
                    LogUtils.d("onStart")
                }
                onComplete { LogUtils.d("onComplete") }
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
        btnVmTest.setOnClickListener {
            startActivity<VmTestActivity>()
        }
    }

    private fun getCancelDialog(): Dialog {
        val view = layoutInflater.inflate(R.layout.progress_dialog, null)
        return AlertDialog.Builder(this@MainActivity)
            .setView(view)
            .setCancelable(false)
            .show()
            .apply {
                val lp = window?.attributes!!
                lp.width = 600
                window!!.attributes = lp
                view.btnCancel.setOnClickListener {
                    dismiss()
                }
            }
    }

}