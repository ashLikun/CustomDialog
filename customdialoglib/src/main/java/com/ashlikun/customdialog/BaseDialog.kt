package com.ashlikun.customdialog

import kotlin.jvm.JvmOverloads
import android.content.Context
import android.app.Dialog
import android.util.DisplayMetrics
import android.os.Bundle
import android.view.WindowManager
import android.view.View
import android.app.Activity
import android.content.ContextWrapper
import android.view.Window
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding

/**
 * 作者　　: 李坤
 * 创建时间: 2018/6/11 0011　上午 11:19
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：封装父类的Dialog
 */
open abstract class BaseDialog @JvmOverloads constructor(context: Context, themeResId: Int = 0) :
    Dialog(context, themeResId) {
    //获取布局view，优先级1
    protected open val layoutView: View? = null

    //获取布局id 优先级2
    protected open val layoutId: Int = View.NO_ID

    //获取布局 优先级3
    protected open val binding: ViewBinding? = null

    //Window
    open val requireWindow: Window
        get() = window!!

    //宿主activity
    open val requireActivity: Activity
        get() = findActivity(context)!!

    protected open val layoutWidth: Int = 0
    protected open val layoutHeight: Int = 0

    /**
     * 获取屏幕信息
     */
    val displayMetrics: DisplayMetrics by lazy {
        DisplayMetrics().apply {
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(
                this
            )
        }
    }

    /**
     * 这个直接在onCreate调用，如果在构造方法会出现被重写的属性没有值
     */
    protected open fun setContentView() {
        when {
            layoutView != null -> setContentView(layoutView!!)
            layoutId != View.NO_ID -> setContentView(layoutId)
            binding != null -> setContentView(binding!!.root)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //这个直接在onCreate调用，如果在构造方法会出现被重写的属性没有值
        setContentView()
        if (layoutWidth != 0 || layoutHeight != 0) {
            val lp = requireWindow.attributes
            if (layoutWidth != 0) {
                lp.width = layoutWidth
            }
            if (layoutHeight != 0) {
                lp.height = layoutHeight
            }
            requireWindow.attributes = lp
        }
        val params = requireWindow.attributes
        initWindowParams(params)
        requireWindow.attributes = params
        initView()
    }


    /**
     * 初始化view
     */
    protected abstract fun initView()


    /**
     * 初始化window参数
     *
     * @param params
     */
    open fun initWindowParams(params: WindowManager.LayoutParams) {}


    private fun findActivity(context: Context?): Activity? {
        if (context == null) {
            return null
        }
        if (context is Activity) {
            return context
        } else if (context is ContextWrapper) {
            return findActivity(context.baseContext)
        }
        return null
    }

    override fun show() {
        val activity = findActivity(context)
        if (activity != null) {
            //如果页面销毁就不弹出
            if (activity.isFinishing) {
                return
            }
        }
        super.show()
    }

    /**
     * 销毁页面
     */
    open fun finish() {
        dismiss()
    }

    /**
     * 自定义查找控件
     */
    open fun <T : View?> f(@IdRes id: Int): T {
        return findViewById(id)
    }

    protected fun dip2px(dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }
}