package com.ashlikun.customdialog.simple

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ashlikun.customdialog.DialogDateTime
import com.ashlikun.customdialog.DialogProgress
import com.ashlikun.customdialog.DialogSelectMore
import com.ashlikun.customdialog.LoadDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onProgressClick(view: View?) {
        val progress = DialogProgress(this)
        progress.show()
    }

    fun onTimeClick(view: View?) {
        val progress = DialogDateTime(this, DialogDateTime.MODE.DATE_Y_M_D_H_M)
        progress.setTitleMain("选择时间")
        progress.show()
    }

    fun onSelectMoreClick(view: View?) {
        val progress = DialogSelectMore(this, items = arrayOf("11111", "22222222", "333333333"))
        progress.show()
    }

    fun onLoadDialogClick(view: View?) {
        val progress = LoadDialog(this)
        progress.show()
    }
}