//package com.ashlikun.customdialog.simple;
//
//import android.app.Dialog;
//import android.content.Context;
//
//import androidx.activity.OnBackPressedDispatcher;
//import androidx.activity.OnBackPressedDispatcherOwner;
//import androidx.annotation.NonNull;
//import androidx.lifecycle.Lifecycle;
//import androidx.lifecycle.LifecycleOwner;
//import androidx.savedstate.SavedStateRegistry;
//import androidx.savedstate.SavedStateRegistryOwner;
//
///**
// * 作者　　: 李坤
// * 创建时间: 2023/12/19　17:55
// * 邮箱　　：496546144@qq.com
// * <p>
// * 功能介绍：
// */
//
//import android.app.Dialog;
//import android.content.Context;
//
//import androidx.activity.OnBackPressedDispatcher;
//import androidx.activity.OnBackPressedDispatcherOwner;
//import androidx.annotation.NonNull;
//import androidx.lifecycle.Lifecycle;
//import androidx.lifecycle.LifecycleOwner;
//import androidx.savedstate.SavedStateRegistry;
//import androidx.savedstate.SavedStateRegistryOwner;
//
///**
// * 作者　　: 李坤
// * 创建时间: 2023/10/31　8:36
// * 邮箱　　：496546144@qq.com
// * <p>
// * 功能介绍：适配androidx.lifecycle:lifecycle.common:2.6.0 之后版本的LifecycleOwner,OnBackPressedDispatcherOwner
// * 利用Java 实现
// */
//public abstract class LifecycleOwner260 extends Dialog implements LifecycleOwner, OnBackPressedDispatcherOwner, SavedStateRegistryOwner {
//
//    public LifecycleOwner260(@NonNull Context context, int themeResId) {
//        super(context, themeResId);
//    }
//
//    @NonNull
//    @Override
//    public Lifecycle getLifecycle() {
//        return getLifecycle260();
//    }
//
//    public abstract Lifecycle getLifecycle260();
//
//    @NonNull
//    @Override
//    public OnBackPressedDispatcher getOnBackPressedDispatcher() {
//        return getOnBackPressedDispatcher260();
//    }
//
//    public abstract OnBackPressedDispatcher getOnBackPressedDispatcher260();
//
//    @NonNull
//    @Override
//    public SavedStateRegistry getSavedStateRegistry() {
//        return getSavedStateRegistry260();
//    }
//
//    public abstract SavedStateRegistry getSavedStateRegistry260();
//
//}
//
