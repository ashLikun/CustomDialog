package com.ashlikun.customdialog.simple

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.ashlikun.customdialog.*
import com.ashlikun.customdialog.simple.databinding.SheetDialogBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val aa = resources.getString(resources.getIdentifier("base_dialog_loadding", "string", packageName))
        val aaa = application.getString(resources.getIdentifier("base_dialog_loadding", "string", packageName))
        val bb = resources.getResourceName(resources.getIdentifier("base_dialog_loadding", "string", packageName))
        val cc = resources.assets.list("")
        resources.getString(resources.getIdentifier("base_dialog_loadding", "string", packageName))

    }


    private val mResources by lazy { LanguageResources(super.getResources()) }

    override fun getResources() = mResources

    fun onProgressClick(view: View?) {
        val progress = DialogProgress(this)
        progress.show()
    }

    fun onTimeClick(view: View?) {
        BaseSheetDialog(this, binding = SheetDialogBinding::class.java).show()
    }

    fun onSelectMoreClick(view: View?) {
        val progress = DialogSelectMore(this, items = arrayOf("11111", "22222222", "333333333"))
        progress.show()
    }

    fun onLoadDialogClick(view: View?) {
        val progress = LoadDialog(this)
        progress.show()
    }

    fun dialogTransparency(view: View?) {
        val progress = DialogTransparency(this)
        progress.show()
    }
}