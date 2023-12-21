package com.zasko.boxtool.utils

import android.R
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager

@SuppressLint("ObsoleteSdkInt")
object SystemBarUtil {

    private const val TYPE_MIUI = 0
    private const val TYPE_FLYME = 1
    private const val TYPE_M = 3 //6.0


    fun setStatusBarColor(act: Activity, colorId: Int) {

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                act.window.statusBarColor = colorId
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                setTranslucentStatus(act)
                val systemBarTintManager = SystemBarTintManager(act)
                systemBarTintManager.isStatusBarTintEnabled = true //显示状态栏
                systemBarTintManager.setStatusBarTintColor(colorId) //设置状态栏颜色

            }
        }
    }


    @TargetApi(19)
    fun setTranslucentStatus(act: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = act.window
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            act.window.decorView.systemUiVisibility = option
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = act.window
            val attributes = window.attributes
            attributes.flags = attributes.flags or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            window.attributes = attributes
        }
    }

    fun setTranslucentSystemBars(act: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            val window: Window = act.getWindow()
            val decorView = window.decorView
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
            val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
            decorView.systemUiVisibility = option
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            //导航栏颜色也可以正常设置
            window.navigationBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window: Window = act.getWindow()
            val attributes = window.attributes
            val flagTranslucentStatus = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            attributes.flags = attributes.flags or flagTranslucentStatus
            window.attributes = attributes
        }
    }

    fun setRootViewFitsSystemWindows(act: Activity, fitSystemWindows: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val winContent = act.findViewById<ViewGroup>(R.id.content)
            if (winContent.childCount > 0) {
                val rootView = winContent.getChildAt(0) as ViewGroup
                rootView.fitsSystemWindows = fitSystemWindows
            }
        }
    }

    fun setStatusBarDarkTheme(act: Activity, dark: Boolean): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setStatusBarFontIconDark(act, TYPE_M, dark)
            } else if (OSUtil.isMiui()) {
                setStatusBarFontIconDark(act, TYPE_MIUI, dark)
            } else if (OSUtil.isFlyme()) {
                setStatusBarFontIconDark(act, TYPE_FLYME, dark)
            } else { //其他情况
                return false
            }
            return true
        }

        return false

    }

    /**
     * 设置 状态栏深色浅色切换
     */
    private fun setStatusBarFontIconDark(act: Activity, type: Int, dark: Boolean) {
        when (type) {
            TYPE_MIUI -> {
                setMiuiUI(act, dark)
                return
            }
            TYPE_FLYME -> {
                setFlymeUI(act, dark)
                return
            }
            TYPE_M -> setCommonUI(act, dark)
            else -> setCommonUI(act, dark)
        }
    }


    //设置MIUI 状态栏深色浅色切换
    private fun setMiuiUI(act: Activity, dark: Boolean) {
        try {
            val window = act.window
            val clazz: Class<*> = act.window.javaClass
            @SuppressLint("PrivateApi") val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            val darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getDeclaredMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            extraFlagField.isAccessible = true
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setFlymeUI(activity: Activity, dark: Boolean) {
        try {
            val window = activity.window
            val lp = window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            value = if (dark) {
                value or bit
            } else {
                value and bit.inv()
            }
            meizuFlags.setInt(lp, value)
            window.attributes = lp
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    //设置6.0 状态栏深色浅色切换
    private fun setCommonUI(activity: Activity, dark: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decorView = activity.window.decorView
            var vis = decorView.systemUiVisibility
            vis = if (dark) {
                vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            if (decorView.systemUiVisibility != vis) {
                decorView.systemUiVisibility = vis
            }
        }
    }

    //获取状态栏高度
    @SuppressLint("InternalInsetResource")
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier(
            "status_bar_height", "dimen", "android"
        )
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    // 获取导航栏高度
    @SuppressLint("InternalInsetResource")
    fun getNavigationBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier(
            "status_bar_height", "dimen", "android"
        )
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}