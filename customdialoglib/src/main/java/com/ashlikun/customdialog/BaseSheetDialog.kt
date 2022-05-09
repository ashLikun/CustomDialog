package com.ashlikun.customdialog

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.*
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.XBottomSheetBehavior
import java.lang.NullPointerException

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
    open val peekHeight: Int? = height ?: (context.resources.displayMetrics.heightPixels * hideThreshold).toInt(),
    //获取布局view，优先级1
    override val layoutView: View? = null,
    //获取布局id 优先级2
    override val layoutId: Int = View.NO_ID,
    //获取布局 优先级3
    binding: Class<out ViewBinding>? = null,
) : BaseDialog(context, themeResId, width, height, gravity, layoutParams, backgroundId, backgroundDrawable, layoutView, layoutId, binding) {
    /**
     * BottomSheetBehavior 的目标view
     */
    open lateinit var sheetView: View

    open lateinit var bottomSheetBehavior: XBottomSheetBehavior<out View>

    override fun createRootView() = CoordinatorLayout(context).apply {
        if (bindingClass != null) {
            binding = DialogUtils.getViewBindingToClass(bindingClass, LayoutInflater.from(context), this, true) as? ViewBinding
        }
        mRootView = when {
            layoutView != null -> layoutView!!.also { addView(it) }
            layoutId != View.NO_ID && layoutId != 0 -> LayoutInflater.from(context).inflate(layoutId, this)
            binding != null -> binding!!.root
            else -> {
                throw NullPointerException()
            }
        }
        sheetView = mRootView!!
        runCatching {
            bottomSheetBehavior = BottomSheetBehavior.from(sheetView) as XBottomSheetBehavior
        }.onFailure {
            bottomSheetBehavior = XBottomSheetBehavior<View>(context).apply {
                isHideable = true
            }
            (mRootView?.layoutParams as? CoordinatorLayout.LayoutParams)?.behavior = bottomSheetBehavior
        }
    }

    override fun baseInitView() {
        super.baseInitView()
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