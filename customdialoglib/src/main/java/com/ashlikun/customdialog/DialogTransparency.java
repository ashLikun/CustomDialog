package com.ashlikun.customdialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;


/**
 * @author　　: 李坤
 * 创建时间: 2018/8/7 15:25
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：透明对话框
 */


public class DialogTransparency extends BaseDialog {

    public DialogTransparency(Context context) {
        this(context, R.style.Dialog_Translucent);
    }

    public DialogTransparency(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected View getLayoutView() {
        View view = new View(getContext());
        return view;
    }

    @Override
    public void initWindowParams(WindowManager.LayoutParams params) {
        super.initWindowParams(params);
        //设置宽度
        params.width = getDisplayMetrics().widthPixels;
        //设置宽度
        params.height = getDisplayMetrics().heightPixels;
        params.gravity = Gravity.CENTER;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {

    }


}
