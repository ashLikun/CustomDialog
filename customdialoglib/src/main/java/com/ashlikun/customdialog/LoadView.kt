package com.ashlikun.customdialog

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Looper
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ashlikun.customdialog.databinding.BaseDialogLoaddingBinding

/**
 * 作者　　: 李坤
 * 创建时间: 2022/7/29　10:02
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：View 形式的加载框
 * 1:可以
 */
open class LoadView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseDialogView(context, attrs, defStyleAttr) {
    override val backgroundDrawable by lazy {
        GradientDrawable().apply {
            setColor(context.resources.getColor(R.color.dialog_load_back_color))
            cornerRadius = DialogUtils.dip2px(context, 10f).toFloat()
        }
    }
    open var contentText: CharSequence? = null
    override val binding by lazy {
        BaseDialogLoaddingBinding.inflate(LayoutInflater.from(context))
    }



    override fun initView() {
        //添加加载控件
        background = ColorDrawable(0x44ff0000)

    }

    /**
     * 设置内容
     */
    open fun setContent(content: CharSequence?): LoadView {
        this.contentText = content
        val c = findViewById<TextView>(R.id.content)
        c?.text = content ?: ""
        if (content == null) c?.visibility = View.GONE
        return this
    }
}