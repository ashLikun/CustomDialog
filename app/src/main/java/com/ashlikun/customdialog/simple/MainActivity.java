package com.ashlikun.customdialog.simple;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ashlikun.customdialog.DialogDateTime;
import com.ashlikun.customdialog.DialogProgress;
import com.ashlikun.customdialog.DialogSelectMore;
import com.ashlikun.customdialog.LoadDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onProgressClick(View view) {
        DialogProgress progress = new DialogProgress(this);
        progress.show();
    }

    public void onTimeClick(View view) {
        DialogDateTime progress = new DialogDateTime(this, DialogDateTime.MODE.DATE_Y_M_D_H_M);
        progress.setTitleMain("选择时间");
        progress.show();
    }

    public void onSelectMoreClick(View view) {
        DialogSelectMore progress = new DialogSelectMore(this, "11111", "22222222", "333333333");
        progress.show();
    }

    public void onLoadDialogClick(View view) {
        LoadDialog progress = new LoadDialog(this);
        progress.show();
    }
}
