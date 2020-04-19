package com.zsc.aac.core

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @author Zsc
 * @date   2019/6/19
 * @desc
 */
abstract class BaseVMActivity<T : ViewDataBinding> : BaseActivity() {

    protected lateinit var binding: T

    override fun initLayout() {
        binding = DataBindingUtil.setContentView(this,layout)
    }

}