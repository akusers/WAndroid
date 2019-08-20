package com.aku.aac.kchttp.core

/**
 * @author Zsc
 * @date   2019/8/7
 * @desc
 */
class KcHttpException(
    val code: Int,
    msg: String
) : RuntimeException(msg) {

    override val message: String
        get() = this.message

}