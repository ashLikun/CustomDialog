package com.ashlikun.customdialog

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.*
import android.widget.LinearLayout
import android.widget.TableLayout
import androidx.annotation.DrawableRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.XBottomSheetBehavior
import java.lang.NullPointerException
import kotlin.math.max

/**
 * 作者　　: 李坤
 * 创建时间: 2022.5.9　14:47
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：bottomSheetBehavior 的基对话框
 */
open class BaseSheetDialog(
    context: Context, themeResId: Int = R.style.Dialog_BottonFormTop,
    override val width: Int? = ViewGroup.LayoutParams.MATCH_PARENT,
    override val height: Int? = (context.resources.displayMetrics.heightPixels * 0.4f).toInt(),
    override val gravity: Int? = Gravity.BOTTOM,
    //优先级最低
    override val layoutParams: WindowManager.LayoutParams? = null,
    @DrawableRes override val backgroundId: Int? = null,
    override val backgroundDrawable: Drawable? = null,
    open val hideThreshold: Float = 0.4f,
    open val peekHeight: Int? = max((context.resources.displayMetrics.heightPixels * hideThreshold).toInt(), height ?: 0),
    //获取布局view，优先级1
    override val layoutView: View? = null,
    //获取布局id 优先级2
    override val layoutId: Int = View.NO_ID,
    //获取布局 优先级3
    binding: Class<out ViewBinding>? = null,
) : BaseDialog(context, themeResId) {


    open lateinit var bottomSheetBehavior: XBottomSheetBehavior<out View>
    override val mRootView = CoordinatorLayout(context)
    override val binding by lazy {
        DialogUtils.getViewBindingToClass(binding, LayoutInflater.from(context), mRootView, true) as? ViewBinding
    }


    /**
     * BottomSheetBehavior 的目标view
     */
    open val sheetView by lazy {
        when {
            layoutView != null -> layoutView!!.also {
                mRootView.addView(it,
                    CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT))
            }
            layoutId != View.NO_ID && layoutId != 0 -> LayoutInflater.from(context).inflate(layoutId, mRootView)
                .run { (this as ViewGroup).getChildAt(0) }
            this.binding != null -> this.binding!!.root.also {
                if (it.parent != mRootView) {
                    (it.parent as? ViewGroup)?.removeView(it)
                    mRootView.addView(it,
                        CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT))
                }
            }
            else -> {
                throw NullPointerException("bot view")
            }
        }
    }

    override fun createRootView() = mRootView.apply {
        runCatching {
            bottomSheetBehavior = BottomSheetBehavior.from(sheetView) as XBottomSheetBehavior
        }.onFailure {
            bottomSheetBehavior = XBottomSheetBehavior<View>(context).apply {
                isHideable = true
            }
            (sheetView?.layoutParams as? CoordinatorLayout.LayoutParams)?.behavior = bottomSheetBehavior
        }
    }

    /**
     * 触摸处理
     */
    var hasCanceledOnTouchOutside: Boolean = true
    var hasCancelable: Boolean = true
    override fun setCanceledOnTouchOutside(cancel: Boolean) {
        super.setCanceledOnTouchOutside(cancel)
        hasCanceledOnTouchOutside = cancel
        hasCancelable = true
    }

    override fun setCancelable(flag: Boolean) {
        super.setCancelable(flag)
        hasCancelable = flag
    }

    override fun baseInitView() {
        super.baseInitView()
        if (hasCanceledOnTouchOutside && hasCancelable) {
            //跟点击消失
            mRootView.setOnClickListener {
                finish()
            }
            //防止内容点击也会消失
            sheetView.setOnClickListener {

            }
        }
        requireWindow.setBackgroundDrawableResource(android.R.color.transparent)
        backgroundId?.also(sheetView::setBackgroundResource)
        backgroundDrawable?.also(sheetView::setBackground)
        bottomSheetBehavior.hideThreshold = hideThreshold
        bottomSheetBehavior.peekHeight = peekHeight ?: height ?: 0
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        finish()
                    }
                }
            }
        })
    }

    override fun initView() {
    }
}