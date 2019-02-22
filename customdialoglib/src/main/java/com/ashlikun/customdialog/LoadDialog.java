package com.ashlikun.customdialog;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


/**
 * 作者　　: 李坤
 * 创建时间: 2017/10/17 16:57
 * 邮箱　　：496546144@qq.com
 * <p>
 * 方法功能：加载中的对话框
 */
public class LoadDialog extends BaseDialog {
    public LoadDialog(Context context) {
        super(context, R.style.Dialog_Loadding);
    }

    @Override
    public void initWindowParams(WindowManager.LayoutParams params) {
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        if (rootView != null && rootView.getBackground() == null) {
            params.gravity = Gravity.CENTER;
            getWindow().setBackgroundDrawable(getGradientDrawable());
        }
        super.initWindowParams(params);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.base_dialog_loadding;
    }

    @Override
    protected void initView() {

    }

    private GradientDrawable getGradientDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(getContext().getResources().getColor(R.color.dialog_load_back_color));
        drawable.setCornerRadius(dip2px(getContext(), 10));
        return drawable;
    }

    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 设置内容
     */
    public LoadDialog setContent(CharSequence content) {
        TextView c = f(R.id.content);
        if (c != null && content != null) {
            c.setText(content);
        }
        if (c != null && content == null) {
            c.setVisibility(View.GONE);
        }
        return this;
    }
}
