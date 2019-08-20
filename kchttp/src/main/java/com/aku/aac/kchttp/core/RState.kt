package com.aku.aac.kchttp.core

/**
 * @author Zsc
 * @date   2019/6/1
 * @desc result state 返回数据的状态
 */
object RState {
    //请求成功
    const val SUCCESS = 0
    //请求失败
    const val ERROR = 1
    //请求取消
    const val CANCEL = -1
}