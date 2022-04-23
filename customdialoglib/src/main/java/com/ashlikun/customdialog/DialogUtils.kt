package com.ashlikun.customdialog

import android.content.Context

/**
 * 作者　　: 李坤
 * 创建时间: 2022/4/23　12:04
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
internal object DialogUtils {
    fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }
}