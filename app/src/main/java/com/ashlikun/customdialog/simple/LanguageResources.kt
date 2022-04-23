package com.ashlikun.customdialog.simple

import android.content.res.*
import android.graphics.Movie
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.RequiresApi
import java.io.InputStream

/**
 * 作者　　: 李坤
 * 创建时间: 2022/4/3　20:55
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：通过包装并拦截Activity和Application resources来Hook getString()
 * 参考 ResourcesWrapper
 * 不能混淆资源名(腾讯的一个混淆资源框架),否则该方案无效
 */
class LanguageResources(val resources: Resources)
    : Resources(resources.assets, resources.displayMetrics, resources.configuration) {
    /**
     * 这里获取服务器的
     */
    fun thisLanguageKVMap(name: String): String? {
//        if (name == "base_dialog_loadding") {
//            return "base_dialog_loadding"
//        }
        return null
    }

    override fun getText(id: Int): CharSequence {
        resources.getResourceEntryName(id)?.let { thisLanguageKVMap(it)?.let { return it } }
        return resources.getText(id)
    }

    override fun getText(id: Int, def: CharSequence?): CharSequence? {
        resources.getResourceEntryName(id)?.let { thisLanguageKVMap(it)?.let { return it } }
        return resources.getText(id, def)
    }

    override fun getString(id: Int): String {
        resources.getResourceEntryName(id)?.let { thisLanguageKVMap(it)?.let { return it } }
        return resources.getString(id)
    }

    override fun getString(id: Int, vararg formatArgs: Any?): String {
        resources.getResourceEntryName(id)?.let { it -> thisLanguageKVMap(it)?.let { return it.format(*formatArgs) } }
        return resources.getString(id, *formatArgs)
    }

    override fun getTextArray(id: Int): Array<CharSequence?> {
        //项目内不要使用array
        return resources.getTextArray(id)
    }

    override fun getStringArray(id: Int): Array<String?> {
        //项目内不要使用array
        return resources.getStringArray(id)
    }

    override fun getQuantityText(id: Int, quantity: Int): CharSequence {
        return resources.getQuantityText(id, quantity)
    }

    override fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any?): String {
        return resources.getQuantityString(id, quantity, *formatArgs)
    }

    override fun getQuantityString(id: Int, quantity: Int): String {
        return resources.getQuantityString(id, quantity)
    }

    override fun getIntArray(id: Int): IntArray {
        return resources.getIntArray(id)
    }

    override fun obtainTypedArray(id: Int): TypedArray {
        return resources.obtainTypedArray(id)
    }

    override fun getDimension(id: Int): Float {
        return resources.getDimension(id)
    }

    override fun getDimensionPixelOffset(id: Int): Int {
        return resources.getDimensionPixelOffset(id)
    }

    override fun getDimensionPixelSize(id: Int): Int {
        return resources.getDimensionPixelSize(id)
    }

    override fun getFraction(id: Int, base: Int, pbase: Int): Float {
        return resources.getFraction(id, base, pbase)
    }

    override fun getDrawable(id: Int): Drawable? {
        return resources.getDrawable(id)
    }

    @RequiresApi(21)
    override fun getDrawable(id: Int, theme: Theme?): Drawable? {
        return resources.getDrawable(id, theme)
    }

    override fun getDrawableForDensity(id: Int, density: Int): Drawable? {
        return resources.getDrawableForDensity(id, density)
    }

    @RequiresApi(21)
    override fun getDrawableForDensity(id: Int, density: Int, theme: Theme?): Drawable? {
        return resources.getDrawableForDensity(id, density, theme)
    }

    override fun getMovie(id: Int): Movie? {
        return resources.getMovie(id)
    }

    override fun getColor(id: Int): Int {
        return resources.getColor(id)
    }

    override fun getColorStateList(id: Int): ColorStateList {
        return resources.getColorStateList(id)
    }

    override fun getBoolean(id: Int): Boolean {
        return resources.getBoolean(id)
    }

    override fun getInteger(id: Int): Int {
        return resources.getInteger(id)
    }

    override fun getLayout(id: Int): XmlResourceParser {
        return resources.getLayout(id)
    }

    override fun getAnimation(id: Int): XmlResourceParser {
        return resources.getAnimation(id)
    }

    override fun getXml(id: Int): XmlResourceParser {
        return resources.getXml(id)
    }

    override fun openRawResource(id: Int): InputStream {
        return resources.openRawResource(id)
    }

    override fun openRawResource(id: Int, value: TypedValue?): InputStream {
        return resources.openRawResource(id, value)
    }

    override fun openRawResourceFd(id: Int): AssetFileDescriptor? {
        return resources.openRawResourceFd(id)
    }

    override fun getValue(id: Int, outValue: TypedValue?, resolveRefs: Boolean) {
        resources.getValue(id, outValue, resolveRefs)
    }

    override fun getValueForDensity(id: Int, density: Int, outValue: TypedValue?, resolveRefs: Boolean) {
        resources.getValueForDensity(id, density, outValue, resolveRefs)
    }

    override fun getValue(name: String?, outValue: TypedValue?, resolveRefs: Boolean) {
        resources.getValue(name, outValue, resolveRefs)
    }

    override fun obtainAttributes(set: AttributeSet?, attrs: IntArray?): TypedArray? {
        return resources.obtainAttributes(set, attrs)
    }

    override fun updateConfiguration(config: Configuration?, metrics: DisplayMetrics?) {
        if (resources == null)
            super.updateConfiguration(config, metrics)
        else
            resources.updateConfiguration(config, metrics)
    }

    override fun getDisplayMetrics(): DisplayMetrics? {
        return resources.displayMetrics
    }

    override fun getConfiguration(): Configuration? {
        return resources.configuration
    }

    override fun getIdentifier(name: String?, defType: String?, defPackage: String?): Int {
        return resources.getIdentifier(name, defType, defPackage)
    }

    override fun getResourceName(resid: Int): String? {
        return resources.getResourceName(resid)
    }

    override fun getResourcePackageName(resid: Int): String? {
        return resources.getResourcePackageName(resid)
    }

    override fun getResourceTypeName(resid: Int): String? {
        return resources.getResourceTypeName(resid)
    }

    override fun getResourceEntryName(resid: Int): String? {
        return resources.getResourceEntryName(resid)
    }

    override fun parseBundleExtras(parser: XmlResourceParser?, outBundle: Bundle?) {
        resources.parseBundleExtras(parser, outBundle)
    }

    override fun parseBundleExtra(tagName: String?, attrs: AttributeSet?, outBundle: Bundle?) {
        resources.parseBundleExtra(tagName, attrs, outBundle)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getColor(id: Int, theme: Theme?): Int {
        return resources.getColor(id, theme)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getColorStateList(id: Int, theme: Theme?): ColorStateList {
        return resources.getColorStateList(id, theme)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getFont(id: Int): Typeface {
        return resources.getFont(id)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun getFloat(id: Int): Float {
        return resources.getFloat(id)
    }
}
