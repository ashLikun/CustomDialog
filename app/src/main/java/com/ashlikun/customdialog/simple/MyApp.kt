package com.ashlikun.customdialog.simple

import android.app.Activity
import android.app.Application
import android.content.res.Resources
import android.os.Bundle

/**
 * 作者　　: 李坤
 * 创建时间: 2022/4/3　21:47
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
class MyApp : Application() {
    private val mResources by lazy { LanguageResources(super.getResources()) }

    override fun getResources() = mResources

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
                super.onActivityPreCreated(activity, savedInstanceState)
                activity.layoutInflater.factory2 = LanguageLayoutInflaterFactory(activity)
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

        })
    }
}