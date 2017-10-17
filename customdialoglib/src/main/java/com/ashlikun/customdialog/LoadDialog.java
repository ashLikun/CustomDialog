package com.ashlikun.customdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.ashlikun.utils.ui.DrawableUtils;
import com.ashlikun.utils.ui.UiUtils;


/**
 * 作者　　: 李坤
 * 创建时间: 2017/10/17 16:57
 * 邮箱　　：496546144@qq.com
 *
 * 方法功能：加载中的对话框
 */
public class LoadDialog extends Dialog {
    public LoadDialog(Context context) {
        super(context, R.style.Dialog_Loadding);
        setContentView(R.layout.base_dialog_loadding);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        getWindow().setBackgroundDrawable(new DrawableUtils(getContext()).getGradientDrawable(R.color.white_90, 0, 10, 0));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiUtils.applyFont(getWindow().getDecorView().findViewById(
                android.R.id.content));

    }

    /*
   * 设置内容
   */
    public LoadDialog setContent(CharSequence content) {
        TextView c = ((TextView) findViewById(R.id.content));
        if (c != null && content != null) {
            c.setText(content);
        }
        if (c != null && content == null) {
            c.setVisibility(View.GONE);
        }
        return this;
    }
}
