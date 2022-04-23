package com.ashlikun.customdialog

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * @author　　: 李坤
 * 创建时间: 2021.12.27 15:34
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：从底部弹起的选择更多的对话框
 */

open class DialogSelectMore(
    context: Context,
    themeResId: Int = R.style.Dialog_SelectMore,
    var bgDrawable: Drawable? = null,
    var onClick: ((position: Int, item: String?) -> Unit)? = null,
    var onCancel: (() -> Unit)? = null,
    open var divColor: Int = -0x222223,
    open var divHeight: Int = -1,
    var items: Array<String>
) : BaseDialog(
    context, themeResId
), View.OnClickListener {
    open val cancelTextView: TextView by lazy {
        f(R.id.dialog_select_cancel)
    }
    open val recyclerView: RecyclerView by lazy {
        f(R.id.recycleView)
    }
    open val rootView: View by lazy {
        f(R.id.dialog_select_root)
    }

    //获取分割线
    open val itemDecoration by lazy {
        MyDiv(context)
    }

    override fun initWindowParams(params: WindowManager.LayoutParams) {
        params.gravity = Gravity.BOTTOM
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        super.initWindowParams(params)
    }

    override val layoutId: Int
        protected get() = R.layout.base_dialog_select_more

    override fun initView() {
        cancelTextView.setOnClickListener(this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        if (bgDrawable != null) rootView.background = bgDrawable
        recyclerView.addItemDecoration(itemDecoration)
        setAdapter()
    }


    open fun setAdapter() {
        recyclerView.adapter = object : RecyclerView.Adapter<MyHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
                return MyHolder(createItem())
            }

            override fun onBindViewHolder(holder: MyHolder, position: Int) {
                holder.onBindViewHolder(position)
            }

            override fun getItemCount(): Int {
                return items.size
            }
        }
    }

    /**
     * 创建一个itemView可以重写
     */
    open fun createItem(): View {
        val textView = TextView(context)
        textView.layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView.gravity = Gravity.CENTER
        val dp10 = DialogUtils.dip2px(context, 15f)
        textView.setPadding(dp10, dp10, dp10, dp10)
        textView.setTextColor(-0xcccccd)
        textView.textSize = 15f

        return textView
    }

    /**
     * 绑定数据,可以重写
     */
    open fun bindItemView(itemView: View, position: Int) {
        itemView.setOnClickListener {
            onClick?.invoke(position, items[position])
            dismiss()
        }
        if (itemView is TextView) itemView.text = items[position]
    }

    override fun onClick(view: View) {
        if (view.id == R.id.dialog_select_cancel) {
            onCancel?.invoke()
        }
        dismiss()
    }


    fun setDivColor(divColor: Int): DialogSelectMore {
        this.divColor = divColor
        return this
    }

    fun setDivHeight(divHeight: Int): DialogSelectMore {
        this.divHeight = divHeight
        return this
    }

    fun setBgDrawable(bgDrawable: Drawable?): DialogSelectMore {
        this.bgDrawable = bgDrawable
        return this
    }


    private inner class MyHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        fun onBindViewHolder(position: Int) {
            bindItemView(itemView, position)
        }
    }

    inner class MyDiv(context: Context) : ItemDecoration() {
        var drawable: GradientDrawable
        private val mBounds = Rect()
        override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            super.onDraw(canvas, parent, state)
            val left: Int
            val right: Int
            if (parent.clipToPadding) {
                left = parent.paddingLeft
                right = parent.width - parent.paddingRight
                canvas.clipRect(
                    left, parent.paddingTop, right,
                    parent.height - parent.paddingBottom
                )
            } else {
                left = 0
                right = parent.width
            }
            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                val position = parent.getChildAdapterPosition(child)
                if (position == items.size - 1) {
                    break
                } else {
                    parent.getDecoratedBoundsWithMargins(child, mBounds)
                    val bottom = mBounds.bottom + Math.round(child.translationY)
                    val top = bottom - drawable.intrinsicHeight
                    drawable.setBounds(left, top, right, bottom)
                    drawable.draw(canvas)
                }
            }
        }

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            if (position == items.size - 1) {
                outRect[0, 0, 0] = 0
            } else {
                super.getItemOffsets(outRect, view, parent, state)
            }
        }

        init {
            drawable = GradientDrawable()
            drawable.setSize(-1, if (divHeight > 0) divHeight else DialogUtils.dip2px(context, 0.5f))
            drawable.setColor(divColor)
        }
    }

}