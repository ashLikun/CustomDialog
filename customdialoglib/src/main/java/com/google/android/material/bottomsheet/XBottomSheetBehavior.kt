package com.google.android.material.bottomsheet

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.NonNull
import kotlin.math.abs

/**
 * 作者　　: 李坤
 * 创建时间: 2020/6/1　11:45
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：改变消失的比例
 */
open class XBottomSheetBehavior<V : View>
@JvmOverloads constructor
    (@NonNull context: Context, attrs: AttributeSet? = null) : BottomSheetBehavior<V>(context, attrs) {
    var hideThreshold = 0.2f
    override fun shouldHide(child: View, yvel: Float): Boolean {
        if (skipCollapsed) {
            return true
        }
        if (child.top < collapsedOffset) {
            // It should not hide, but collapse.
            return false
        }
        val newTop = child.top + yvel * 0.1f
        return abs(newTop - collapsedOffset) / peekHeight.toFloat() > hideThreshold
    }

}