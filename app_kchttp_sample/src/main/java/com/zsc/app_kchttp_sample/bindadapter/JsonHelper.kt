@file:JvmName("JsonHelper")

package com.zsc.app_kchttp_sample.bindadapter

import com.zsc.common.util.toJson

object JsonHelper {
    @JvmStatic
    fun toJson(any: Any?) = any.toJson()
}