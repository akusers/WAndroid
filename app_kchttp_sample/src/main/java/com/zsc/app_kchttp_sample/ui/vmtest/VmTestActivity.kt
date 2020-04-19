package com.zsc.app_kchttp_sample.ui.vmtest

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zsc.aac.core.BaseVMActivity
import com.zsc.aac.kchttp.ext.doError
import com.zsc.aac.kchttp.ext.doSuccess
import com.zsc.app_kchttp_sample.R
import com.zsc.app_kchttp_sample.databinding.VmTestActBinding
import com.zsc.common.util.toJson
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.vm_test_act.*

class VmTestActivity : BaseVMActivity<VmTestActBinding>() {

    override val layout: Int = R.layout.vm_test_act

    override fun initData(savedInstanceState: Bundle?) {

        val viewModel = ViewModelProvider(this)
            .get(VmTestViewModel::class.java)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        viewModel.listBanner.observe(this, Observer {
            it.doSuccess { list ->
                tvContent.text = list.toJson()
            }.doError { error ->
                ToastUtils.showShort(error.toJson())
            }
        })
        viewModel.loadBanner()
        //这里可以直接绑定一个显示的dialog
//            .bindDialog()
    }
}