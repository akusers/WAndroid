package com.aku.common.util

import android.util.Log
import com.blankj.utilcode.util.LogUtils

/**
 * @author Zsc
 * @date   2019/6/26
 * @desc
 */
private val globalTag by lazy {
    LogUtils.getConfig().globalTag!!
}

fun logV(vararg contents: Any?) = LogUtils.log(Log.VERBOSE, globalTag, *contents)
fun logVTag(tag: String, vararg contents: Any?) = LogUtils.log(Log.VERBOSE, tag, *contents)

fun logD(vararg contents: Any?) = LogUtils.log(Log.DEBUG, globalTag, *contents)
fun logDTag(tag: String, vararg contents: Any?) = LogUtils.log(Log.DEBUG, tag, *contents)

fun logI(vararg contents: Any?) = LogUtils.log(Log.INFO, globalTag, *contents)
fun logITag(tag: String, vararg contents: Any?) = LogUtils.log(Log.INFO, tag, *contents)

fun logW(vararg contents: Any?) = LogUtils.log(Log.WARN, globalTag, *contents)
fun logWTag(tag: String, vararg contents: Any?) = LogUtils.log(Log.WARN, tag, *contents)

fun logE(vararg contents: Any?) = LogUtils.log(Log.ERROR, globalTag, *contents)
fun logETag(tag: String, vararg contents: Any?) = LogUtils.log(Log.ERROR, tag, *contents)

fun logA(vararg contents: Any?) = LogUtils.log(Log.ASSERT, globalTag, *contents)
fun logATag(tag: String, vararg contents: Any?) = LogUtils.log(Log.ASSERT, tag, *contents)
