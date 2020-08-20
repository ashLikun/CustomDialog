package com.ashlikun.customdialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/6/11 0011　上午 11:19
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：封装父类的Dialog
 */
public abstract class BaseDialog extends Dialog {
    private DisplayMetrics displayMetrics;

    public BaseDialog(@NonNull Context context) {
        this(context, 0);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        if (getLayoutId() == 0) {
            setContentView(getLayoutView());
        } else {
            setContentView(getLayoutId());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutWidth() != 0 || getLayoutHeight() != 0) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            if (getLayoutWidth() != 0) {
                lp.width = getLayoutWidth();
            }
            if (getLayoutHeight() != 0) {
                lp.height = getLayoutHeight();
            }
            getWindow().setAttributes(lp);
        }
        WindowManager.LayoutParams params = getWindow().getAttributes();
        initWindowParams(params);
        getWindow().setAttributes(params);
        initView();
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:06
     * <p>
     * 方法功能：获取布局id
     */
    protected abstract int getLayoutId();

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:16
     * <p>
     * 方法功能：初始化view
     */
    protected abstract void initView();


    protected int getLayoutWidth() {
        return 0;
    }

    protected int getLayoutHeight() {
        return 0;
    }

    /**
     * 获取布局view，当布局id ==0 的时候会调用当前方法
     *
     * @return
     */
    protected View getLayoutView() {
        return null;
    }


    /**
     * 初始化window参数
     *
     * @param params
     */
    public void initWindowParams(WindowManager.LayoutParams params) {
    }


    /**
     * 获取屏幕信息
     *
     * @return
     */
    public DisplayMetrics getDisplayMetrics() {
        if (displayMetrics == null) {
            displayMetrics = new DisplayMetrics();
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics;
    }

    /**
     * 方法功能：从context中获取activity，如果context不是activity那么久返回null
     */
    public Activity findActivity() {
        return findActivity(getContext());
    }

    private Activity findActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return findActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    @Override
    public void show() {
        Activity activity = findActivity();
        if (activity != null) {
            //如果页面销毁就不弹出
            if (activity.isFinishing()) {
                return;
            }
        }
        super.show();
    }

    /**
     * 销毁页面
     */
    public void finish() {
        dismiss();
    }


    /**
     * 自定义查找控件
     */
    public <T extends View> T f(@IdRes int id) {
        return findViewById(id);
    }
}