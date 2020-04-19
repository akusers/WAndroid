package com.zsc.app_kchttp_sample.data
/**
 */
data class Page<T>(
    var curPage: Int,
    var datas: MutableList<T>,
    var offset: Int,
    var pageCount: Int,
    var size: Int,
    var total: Int,
    var over: Boolean
)

