package com.zsc.aac.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * @author Zsc
 * @date   2019/6/19
 * @desc
 */
abstract class BaseActivity : AppCompatActivity() {
    @get:LayoutRes
    abstract val layout: Int

    /**
     * 初始化数据
     */
    abstract fun initData(savedInstanceState: Bundle?)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        initData(savedInstanceState)
    }

    open fun initLayout() {
        setContentView(layout)
    }

}