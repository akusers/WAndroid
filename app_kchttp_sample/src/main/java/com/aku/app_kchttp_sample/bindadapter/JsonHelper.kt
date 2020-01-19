@file:JvmName("JsonHelper")

package com.aku.app_kchttp_sample.bindadapter

import com.aku.common.util.toJson

object JsonHelper {
    @JvmStatic
    fun toJson(any: Any?) = any.toJson()
}