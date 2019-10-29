package com.aku.aac.kchttp.ext

import android.app.Dialog
import kotlinx.coroutines.Job

/**
 * 绑定dialog,dialog消失则取消
 */
fun Job.bindDialog(dialog: Dialog) {
    dialog.setOnDismissListener {
        cancel()
    }
    withDialog(dialog)
}

/**
 * 伴随dialog,dialog消失继续执行
 */
fun Job.withDialog(dialog: Dialog){
    invokeOnCompletion {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }
}
