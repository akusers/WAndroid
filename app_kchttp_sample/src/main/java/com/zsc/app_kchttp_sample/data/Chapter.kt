package com.zsc.app_kchttp_sample.data

data class Chapter(
    var children: List<Chapter>,
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int
)