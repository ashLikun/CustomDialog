package com.ashlikun.customdialog

import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle

/**
 * 作者　　: 李坤
 * 创建时间: 2023/10/30　16:16
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：适配androidx.lifecycle:lifecycle.common:2.6.0 之后版本的LifecycleOwner
 */
interface LifecycleOwner260 {
    val lifecycle: Lifecycle

    @NonNull
    fun getLifecycle(): Lifecycle

}