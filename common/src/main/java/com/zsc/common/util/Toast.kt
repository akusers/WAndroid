package com.zsc.common.util

import androidx.annotation.StringRes
import com.blankj.utilcode.util.ToastUtils

/**
 * @author Zsc
 * @date   2019/6/26
 * @desc
 */
fun toastShort(@StringRes resId: Int, vararg args: Any?) = ToastUtils.showShort(resId, *args)

fun toastShort(format: String?, vararg args: Any?) = ToastUtils.showShort(format, *args)

fun toastLong(@StringRes resId: Int, vararg args: Any?) = ToastUtils.showLong(resId, *args)

fun toastLong(format: String?, vararg args: Any?) = ToastUtils.showLong(format, *args)