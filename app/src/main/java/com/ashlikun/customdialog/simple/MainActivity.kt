package com.ashlikun.customdialog.simple

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.ashlikun.customdialog.*
import com.ashlikun.customdialog.simple.databinding.ActivityMainBinding
import com.ashlikun.customdialog.simple.databinding.SheetDialogBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
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
//        BaseSheetDialog(this, binding = SheetDialogBinding::class.java).show()
        MyBaseSheetDialog(this).show()
    }

    fun onSelectMoreClick(view: View?) {
        val progress = DialogSelectMore(this, items = arrayOf("11111", "22222222", "333333333"))
        progress.show()
    }

    fun onLoadDialogClick(view: View?) {
        val progress = LoadDialog(this)
        progress.show()
    }

    fun onLoadDialogViewClick(view: View?) {
        val loadView = LoadView(this)
//        loadView.attachedView = binding.action2
//        loadView.isAutoTopMargin = true

    }

    fun dialogTransparency(view: View?) {
        val progress = DialogTransparency(this)
        progress.show()
    }
}

class MyBaseSheetDialog(context: Context) : BaseSheetDialog(context, backgroundId = R.color.dialog_progress_text_color) {
    override val binding by lazy {
        Log.e("aaaa", "2222222")
        SheetDialogBinding.inflate(layoutInflater)
    }

}