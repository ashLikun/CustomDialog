package com.ashlikun.customdialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Looper
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding

/**
 * 作者　　: 李坤
 * 创建时间: 2018/6/11 0011　上午 11:19
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：以view的形式实现Dialog
 */
open abstract class BaseDialogView @JvmOverloads
constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
    open val width: Int? = null,
    open val height: Int? = null,
    open val gravity: Int? = null,
    //优先级最低
    open val layoutParamsX: LayoutParams? = null,
    @DrawableRes
    open val backgroundId: Int? = null,
    open val backgroundDrawable: Drawable? = null,
    //获取布局view，优先级1
    protected open val layoutView: View? = null,
    //获取布局id 优先级2
    protected open val layoutId: Int = View.NO_ID,
    //获取布局 优先级3
    binding: Class<out ViewBinding>? = null
) : FrameLayout(context, attrs, defStyleAttr) {

    open val binding by lazy {
        DialogUtils.getViewBindingToClass(binding, LayoutInflater.from(context), null, false) as? ViewBinding
    }
    open val mRootView by lazy {
        when {
            layoutView != null -> layoutView!!
            layoutId != View.NO_ID && layoutId != 0 -> LayoutInflater.from(context).inflate(layoutId, null)
            this.binding != null -> this.binding!!.root
            else -> {
                throw NullPointerException("bot view")
            }
        }
    }
    open val isShowing
        get() = visibility == View.VISIBLE && parent != null

    //宿主activity
    open val requireActivity: Activity
        get() = DialogUtils.getActivity(context)!!

    //Window
    open val requireWindow: Window
        get() = requireActivity.window!!

    //当前加载框附属在哪个view上面
    open var attachedView: View = requireActivity.window.decorView as ViewGroup
    open var attachedLayoutParams: LayoutParams = LayoutParams(-1, -1)

    //是否点击外围取消
    open var isCancelable = true

    //一样，为了兼容对话框
    open var isCanceledOnTouchOutside = true

    //是否自动适配顶部的AppBar高度和状态栏高度，留出顶部的距离,只有attachedView == decorView 才可以
    open var isAutoTopMargin = false
        set(value) {
            field = value
        }
    private var isCreate = false

    open var maskBackground = ColorDrawable(0x88000000.toInt())

    open var onCancelListener: (() -> Unit)? = null
    open var onShowlListener: (() -> Unit)? = null

    /**
     * 这个直接在onCreate调用，如果在构造方法会出现被重写的属性没有值
     */
    protected open fun setContentView() {
        //添加加载控件
        val mgravity = gravity
        addView(createRootView(), LayoutParams(-2, -2).apply {
            gravity = mgravity ?: Gravity.CENTER
        })
    }

    open fun createRootView(): View {
        return mRootView!!
    }


    open fun baseInitView() {
        if (layoutParamsX != null) {
            attachedLayoutParams = layoutParamsX!!
        }
        width?.also {
            attachedLayoutParams.width = it
        }
        height?.also {
            attachedLayoutParams.height = it
        }
        gravity?.also {
            attachedLayoutParams.gravity = it
        }
        initWindowParams(attachedLayoutParams)
        layoutParams = attachedLayoutParams
        backgroundId?.also(mRootView::setBackgroundResource)
        backgroundDrawable?.also(mRootView::setBackground)
        if (background == null && maskBackground != null) {
            background = maskBackground
        }
        mRootView.setOnClickListener {
            //点击加载框无操作
        }
        //点击其他地方
        setOnClickListener {
            if (isCancelable && isCanceledOnTouchOutside)
                finish()
        }
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
    open fun initWindowParams(params: MarginLayoutParams) {}


    open fun dismiss() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            (parent as? ViewGroup)?.removeView(this)
            onCancelListener?.invoke()
        } else {
            post {
                (parent as? ViewGroup)?.removeView(this)
            }
            onCancelListener?.invoke()
        }
    }


    open fun show() {
        val activity = requireActivity
        if (activity != null) {
            //如果页面销毁就不弹出
            if (activity.isFinishing) {
                return
            }
        }
        if (isShowing) return
        if (Looper.myLooper() == Looper.getMainLooper()) {
            startAttachView()
        } else {
            post {
                startAttachView()
            }
        }
    }

    private fun startAttachView() {
        if (!isCreate) {
            isCreate = true
            //这个直接在onCreate调用，如果在构造方法会出现被重写的属性没有值
            setContentView()
            baseInitView()
            initView()
        }
        (parent as? ViewGroup)?.removeView(this)
        setAutoMarginTop()
        runCatching {
            when (attachedView) {
                is FrameLayout -> {
                    (attachedView as FrameLayout).addView(this, attachedLayoutParams)
                }
                is RelativeLayout -> {
                    (attachedView as RelativeLayout).addView(this, attachedLayoutParams)
                }
                //约束布局
                is ConstraintLayout -> {
                    val params = ConstraintLayout.LayoutParams(attachedLayoutParams)
                    params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                    params.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
                    params.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
                    (attachedView as ConstraintLayout).addView(this, params)
                }
                //其他布局,更具附属布局相对于屏幕的位置设置Margin
                else -> {
                    DialogUtils.getViewSize(attachedView) { width, height ->
                        val decorView = requireActivity.window.decorView as ViewGroup
                        val params = LayoutParams(attachedLayoutParams)
                        val screen = intArrayOf(0, 0)
                        attachedView.getLocationOnScreen(screen)
                        params.topMargin = screen[1]
                        params.leftMargin = screen[0] / 2
                        params.rightMargin = screen[0] / 2
                        params.height = attachedView.height
                        params.width = attachedView.width
                        decorView.addView(this, params)
                    }
                }
            }
        }
        onShowlListener?.invoke()
    }

    /**
     * 自动计算顶部高度
     */
    open fun setAutoMarginTop() {
        if (isAutoTopMargin && attachedView == requireActivity.window.decorView) {
            if (attachedLayoutParams is MarginLayoutParams) {
                attachedLayoutParams.topMargin = DialogUtils.getActionBarSize(context) + DialogUtils.getStatusHeight(context)
                attachedView.layoutParams = attachedLayoutParams
            }
        }
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


}