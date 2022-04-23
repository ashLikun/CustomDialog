package com.ashlikun.customdialog

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

/**
 * @author　　: 李坤
 * 创建时间: 2018/8/7 15:25
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：透明对话框
 */
open class DialogTransparency @JvmOverloads constructor(
    context: Context,
    themeResId: Int = R.style.Dialog_Fullscreen
) : BaseDialog(context, themeResId, width = ViewGroup.LayoutParams.MATCH_PARENT, height = ViewGroup.LayoutParams.MATCH_PARENT) {

    override val layoutView: View
        protected get() = View(context)


    override val layoutId: Int
        protected get() = 0

    override fun initView() {}
}