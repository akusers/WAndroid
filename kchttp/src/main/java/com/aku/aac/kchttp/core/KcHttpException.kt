package com.aku.aac.kchttp.core

/**
 * @author Zsc
 * @date   2019/8/7
 * @desc
 */
class KcHttpException(
    val code: Int,
    override val message: String
) : RuntimeException(message)