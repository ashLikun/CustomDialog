package com.ashlikun.customdialog;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * 作者　　: 李坤
 * 创建时间: 2023/10/31　8:36
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：适配androidx.lifecycle:lifecycle.common:2.6.0 之后版本的LifecycleOwner
 * 利用Java 实现
 */
public abstract class LifecycleOwner260 extends Dialog implements LifecycleOwner {

    public LifecycleOwner260(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public Lifecycle getLifecycle() {
        return getLifecycle260();
    }

    public abstract Lifecycle getLifecycle260();
}
