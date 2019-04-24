package com.ashlikun.customdialog;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 作者　　: 李坤
 * 创建时间: 2016/9/19 16:28
 * <p>
 * 方法功能：从底部弹起的选择更多的兑换框
 */
public class DialogSelectMore extends BaseDialog implements View.OnClickListener {
    TextView cancel;

    private String[] items;
    private RecyclerView recyclerView;

    private OnClickCallback clickCallback;
    private int divColor = 0xffdddddd;
    private int divHeight = -1;


    public DialogSelectMore(Context context, String... items) {
        this(context, R.style.Dialog_SelectMore);
        this.items = items;
    }

    public DialogSelectMore(Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    public void initWindowParams(WindowManager.LayoutParams params) {
        params.gravity = Gravity.BOTTOM;
        params.width = getDisplayMetrics().widthPixels;
        params.height = (ActionBar.LayoutParams.WRAP_CONTENT);
        super.initWindowParams(params);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.base_dialog_select_more;
    }

    @Override
    protected void initView() {
        cancel = f(R.id.dialog_select_cancel);
        recyclerView = f(R.id.recycleView);
        cancel.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.addItemDecoration(getItemDecoration());
        setAdapter();
    }

    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private void setAdapter() {
        recyclerView.setAdapter(new RecyclerView.Adapter<MyHolder>() {
            @Override
            public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                return new MyHolder(createItem());
            }

            @Override
            public void onBindViewHolder(MyHolder holder, int position) {
                holder.onBindViewHolder(position);
            }

            @Override
            public int getItemCount() {
                return items.length;
            }
        });
    }

    public TextView getCancelTextView() {
        return cancel;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    /**
     * 创建一个itemView可以重写
     */
    public View createItem() {
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        int dp10 = dip2px(getContext(), 15);
        textView.setPadding(dp10, dp10, dp10, dp10);
        textView.setTextColor(0xff333333);
        textView.setTextSize(15);
        return textView;
    }

    /**
     * 绑定数据,可以重写
     *
     * @param itemView
     * @param position
     */
    public void bindItemView(View itemView, final int position) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCallback != null) {
                    clickCallback.onClick(position, items[position]);
                }
                dismiss();
            }
        });
        if (itemView instanceof TextView) {
            ((TextView) itemView).setText(items[position]);
        }
    }

    @Override
    public void onClick(View view) {
        if (clickCallback != null) {
            if (view.getId() == R.id.dialog_select_cancel) {
                clickCallback.onCancel();
            }
        }
        dismiss();
    }

    public void setClickCallback(OnClickCallback clickCallback) {

        this.clickCallback = clickCallback;
    }

    /**
     * 获取分割线
     *
     * @return
     */
    public RecyclerView.ItemDecoration getItemDecoration() {
        MyDiv itemDecoration = new MyDiv(getContext());

        return itemDecoration;
    }

    public DialogSelectMore setDivColor(int divColor) {
        this.divColor = divColor;
        return this;
    }

    public DialogSelectMore setDivHeight(int divHeight) {
        this.divHeight = divHeight;
        return this;
    }

    /**
     * 点击事件回调外部
     */
    public interface OnClickCallback {

        void onCancel();

        /**
         * @param position 点击的位置
         * @param item     标题
         */
        void onClick(int position, String item);
    }

    private class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
        }

        public void onBindViewHolder(int position) {
            bindItemView(itemView, position);
        }
    }

    public class MyDiv extends RecyclerView.ItemDecoration {
        GradientDrawable drawable;
        private final Rect mBounds = new Rect();

        public MyDiv(Context context) {
            drawable = new GradientDrawable();
            drawable.setSize(-1, divHeight > 0 ? divHeight : dip2px(getContext(), 0.5f));
            drawable.setColor(divColor);
        }

        @Override
        public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(canvas, parent, state);
            final int left;
            final int right;
            if (parent.getClipToPadding()) {
                left = parent.getPaddingLeft();
                right = parent.getWidth() - parent.getPaddingRight();
                canvas.clipRect(left, parent.getPaddingTop(), right,
                        parent.getHeight() - parent.getPaddingBottom());
            } else {
                left = 0;
                right = parent.getWidth();
            }
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                int position = parent.getChildAdapterPosition(child);
                if (position == items.length - 1) {
                    break;
                } else {
                    parent.getDecoratedBoundsWithMargins(child, mBounds);
                    final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
                    final int top = bottom - drawable.getIntrinsicHeight();
                    drawable.setBounds(left, top, right, bottom);
                    drawable.draw(canvas);
                }
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            if (position == items.length - 1) {
                outRect.set(0, 0, 0, 0);
            } else {
                super.getItemOffsets(outRect, view, parent, state);
            }
        }
    }
}
