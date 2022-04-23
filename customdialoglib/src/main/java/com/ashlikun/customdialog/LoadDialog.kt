package com.ashlikun.customdialog

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TextView

/**
 * @author　　: 李坤
 * 创建时间: 2021.12.27 14:48
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：加载中的对话框
 */
open class LoadDialog(context: Context) : BaseDialog(context, R.style.Dialog_Loadding) {
    override val backgroundDrawable by lazy {
        GradientDrawable().apply {
            setColor(context.resources.getColor(R.color.dialog_load_back_color))
            cornerRadius = DialogUtils.dip2px(context, 10f).toFloat()
        }
    }
    override val gravity = Gravity.CENTER

    override val layoutId: Int
        protected get() = R.layout.base_dialog_loadding

    override fun initView() {}


    /**
     * 设置内容
     */
    fun setContent(content: CharSequence?): LoadDialog {
        val c = f<TextView>(R.id.content)
        c?.text = content ?: ""
        if (c != null && content == null) c.visibility = View.GONE
        return this
    }
}