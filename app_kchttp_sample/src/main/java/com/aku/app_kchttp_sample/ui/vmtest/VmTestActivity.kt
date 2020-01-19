package com.aku.app_kchttp_sample.ui.vmtest

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aku.aac.core.BaseActivity
import com.aku.app_kchttp_sample.R
import com.aku.common.util.toJson
import kotlinx.android.synthetic.main.vm_test_act.*

class VmTestActivity : BaseActivity() {

    override val layout: Int = R.layout.vm_test_act

    override fun initData(savedInstanceState: Bundle?) {

        val viewModel = ViewModelProvider(this)
            .get(VmTestViewModel::class.java)
        viewModel.listBanner.observe(this, Observer{
            tvContent.text=it.toJson()
        })
        viewModel.loadBanner()
        //这里可以直接绑定一个显示的dialog
//            .bindDialog()
    }
}