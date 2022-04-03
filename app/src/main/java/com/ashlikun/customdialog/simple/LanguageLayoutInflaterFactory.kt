package com.ashlikun.customdialog.simple

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

/**
 * 作者　　: 李坤
 * 创建时间: 2022/4/3　21:12
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：拦截xml解析来Hook @string/
 */
class LanguageLayoutInflaterFactory(val activity: Activity) : LayoutInflater.Factory2 {
    private val APP_KEY = "http://schemas.android.com/apk/res-auto"
    private val ANDROID_KEY = "http://schemas.android.com/apk/res/android"

    //sdk的activity使用的布局生成器
    private val delegate by lazy(LazyThreadSafetyMode.NONE) { AppCompatDelegate.create(activity, null) }

    //获取默认的createView方法,可以在这里判断并适配换肤框架等
    fun checkAndCreateView(parent: View?, name: String?, context: Context, attrs: AttributeSet): View? {
        return when (activity) {
            is AppCompatActivity -> activity.delegate.createView(parent, name, context, attrs)
            else -> delegate.createView(parent, name, context, attrs)
        }
    }

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        return checkAndReturnView(name, context, attrs, checkAndCreateView(parent, name, context, attrs))
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return null
    }

    fun checkAndReturnView(name: String, context: Context, attrs: AttributeSet, view: View?): View? {
        val view = view ?: try {
            createViewGroup(name, context, attrs)
        } catch (e: Exception) {
            null
        } ?: return null

        handlerXmlText(view, attrs)
        return view
    }

    private fun handlerXmlText(view: View, attrs: AttributeSet) {
        //这里就是xml里面引用String的地方
        if (view is TextView) {
            getStringValue(attrs, true, "text")?.let(view::setText)
            getStringValue(attrs, true, "hint")?.let(view::setHint)
        }
        //下面两个是自定义View使用了@string/
//        if (view is MyView) {
//            val value = getStringValue(attrs, false, "left_text")
//            if (value != null)
//                view.setLeftText(value)
//        }

        //或者下面这种写法
        /*if (view is TextView) {
            getStringValue(attrs, true, "text")?.let(view::setText)
            getStringValue(attrs, true, "hint")?.let(view::setHint)
        }
        if (view is ItemView)
            getStringValue(attrs, false, "left_text")?.let(view::setLeftText)
        if (view is SelectView)
            getStringValue(attrs, false, "middle_text")?.let(view::setMiddleText)*/
    }

    private fun Int.toText() = activity.getString(this)

    private fun getStringValue(attrs: AttributeSet, isAndroidSystem: Boolean, valueName: String): String? {
        val value = attrs.getAttributeValue(if (isAndroidSystem) ANDROID_KEY else APP_KEY, valueName)
        if (value?.startsWith("@") == true)
            try {
                return value.substring(1).toIntOrNull()?.toText()
            } catch (e: Exception) {
            }
        return null
    }

    private fun createViewGroup(name: String, context: Context, attrs: AttributeSet): View? {
        return when (name) {
            //下面两个是自定义View使用了@string/
            //且下面两个是ViewGroup,如果是View则会在delegate中就创建完成
            //可能还需要加AppCompatTextView
//            "com.xxx.MyView" -> MyView(context, attrs)
            else -> null
        }
        //想省事的话直接全部生成:
        //Class.forName(if (name.contains('.')) name else "android.widget.$name")
        //                .getConstructor(Context::class.java, AttributeSet::class.java)
        //                .newInstance(context, attrs) as View
    }
}
