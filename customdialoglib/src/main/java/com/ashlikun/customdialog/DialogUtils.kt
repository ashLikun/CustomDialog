package com.ashlikun.customdialog

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.viewbinding.ViewBinding
import java.lang.reflect.AccessibleObject
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier

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
    fun screenHeight(context: Context) =  context.resources.displayMetrics.heightPixels

    private var viewBindingGetMap = mutableMapOf<Class<*>, AccessibleObject>()


    /**
     * 方法功能：从context中获取activity，如果context不是activity那么久返回null
     */
    fun getActivity(context: Context?): Activity? {
        if (context == null) {
            return null
        }
        if (context is Activity) {
            return context
        } else if (context is ContextWrapper) {
            return getActivity(context.baseContext)
        } else if (context is ContextThemeWrapper) {
            return getActivity(context.baseContext)
        }
        return null
    }


    /**
     * 反射查找ViewBinding的view
     *
     * @return 实例是ViewBinding
     */
    fun getViewBinding(`object`: Any?): ViewBinding? {
        if (`object` == null) {
            return null
        }
        try {
            //检测是否有ViewBinding 库
            val viewBindingCls: Class<*> = ViewBinding::class.java
            val objCls: Class<*> = `object`.javaClass
            //查找缓存
            var accessibleObject = viewBindingGetMap[objCls]
            if (accessibleObject != null) {
                accessibleObject.isAccessible = true
                if (accessibleObject is Method) {
                    return accessibleObject.invoke(`object`) as ViewBinding
                } else if (accessibleObject is Field) {
                    return accessibleObject[`object`] as ViewBinding
                }
            }
            var cls: Class<*>? = objCls
            while (cls != null && cls != Any::class.java) {
                //获取方法->返回值是
                val declaredMethods = cls.declaredMethods
                for (m in declaredMethods) {
                    if (viewBindingCls.isAssignableFrom(m.returnType)) {
                        m.isAccessible = true
                        val view = m.invoke(`object`) as ViewBinding
                        viewBindingGetMap[objCls] = m
                        return view
                    }
                }
                //获取父类的
                cls = cls.superclass
            }
        } catch (e: Exception) {
            return null
        } catch (e: NoClassDefFoundError) {
            return null
        }
        return null
    }


    /**
     * 获取3个参数的静态方法
     * @return ViewBinding
     */
    fun getViewBindingToClass(
        cls: Class<*>?,
        layoutInflater: LayoutInflater?,
        parent: ViewGroup?,
        attachToParent: Boolean
    ): Any? {
        if (cls == null || layoutInflater == null) {
            return null
        }
        //从缓存获取
        try {
            var inflate: Method? = null
            val existinflate = viewBindingGetMap[cls]
            if (existinflate is Method) {
                inflate = existinflate
            }
            if (inflate == null) {
                //直接取方法
                inflate = cls.getDeclaredMethod(
                    "inflate",
                    LayoutInflater::class.java,
                    ViewGroup::class.java,
                    Boolean::class.javaPrimitiveType
                )
                //这里循环全部方法是为了混淆的时候无影响
                if (inflate == null) {
                    val declaredMethods = cls.declaredMethods
                    for (declaredMethod in declaredMethods) {
                        val modifiers = declaredMethod.modifiers
                        if (Modifier.isStatic(modifiers)) {
                            val parameterTypes = declaredMethod.parameterTypes
                            if (parameterTypes != null && parameterTypes.size == 3) {
                                if (LayoutInflater::class.java.isAssignableFrom(parameterTypes[0]) &&
                                    ViewGroup::class.java.isAssignableFrom(parameterTypes[1]) &&
                                    Boolean::class.javaPrimitiveType!!.isAssignableFrom(
                                        parameterTypes[2]
                                    )
                                ) {
                                    inflate = declaredMethod
                                    break
                                }
                            }
                        }
                    }
                }
                //添加到缓存
                if (inflate != null) {
                    viewBindingGetMap[cls] = inflate
                }
            }
            if (inflate != null) {
                return inflate.invoke(null, layoutInflater, parent, attachToParent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}