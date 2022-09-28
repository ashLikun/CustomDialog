package com.ashlikun.customdialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.ashlikun.numberprogressbar.NumberProgressBar

/**
 * 作者　　: 李坤
 * 创建时间: 2017/7/7 17:35
 * 邮箱　　：496546144@qq.com
 *
 *
 * 方法功能：加载精度对话框
 */
open class DialogProgress @JvmOverloads constructor(
    context: Context,
    theme: Int = R.style.Dialog_Progress
) : BaseDialog(context, theme) {
    private val progressBar: NumberProgressBar by lazy {
        f(R.id.progressBar)
    }
    private val titleView: TextView by lazy {
        f(R.id.title)
    }

    private val titleText: CharSequence
        set(value) = {

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.base_dialog_progress

    override fun initView() {

    }

    fun setProgress(progress: Int): DialogProgress {
        progressBar.progress = (progress * Math.round(maxValus / 100.0)).toInt()
        return this
    }

    fun setTitleText(title: String?): DialogProgress {
        if (isShowing) {
            if (title.isNullOrEmpty()) {
                titleView.visibility = View.GONE
            } else {
                titleView.visibility = View.VISIBLE
                titleView.text = title
            }
        } else {

        }

        return this
    }

    /**
     *最大尺度
     */
    var maxValus: Int
        get() = progressBar.max
        set(value) {
            progressBar.max = value
        }


    /**
     * 获取当前尺度
     */
    val progress: Int
        get() = progressBar.progress
}