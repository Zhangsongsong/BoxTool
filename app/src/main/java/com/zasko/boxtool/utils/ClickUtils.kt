package com.zasko.boxtool.utils

import androidx.annotation.NonNull

object ClickUtils {

    private val viewLastClickMaps: MutableMap<Int, Long> = HashMap()

    /**
     * 是否是快速点击
     *
     *
     * 注意快速点击是相对于全局的
     */
    fun isFastDoubleClick(view: Any): Boolean {
        return isFastDoubleClick(500L, view)
    }

    /**
     * 是否是正常点击（非快速双击）
     *
     *
     * 注意快速点击是相对于全局的
     */
    fun isCommonClick(view: Any?): Boolean {
        return !isFastDoubleClick(500L, view)
    }

    /**
     * 根据指定的时间间隔判断 是否是快速点击
     *
     * @param gap 时间间隔
     */
    fun isFastDoubleClick(gap: Long, view: Any?): Boolean {

        // 防止错误调用
        val current = System.currentTimeMillis()
        val viewId = getViewId(view)
        val lastClickTime = if (viewLastClickMaps.containsKey(viewId)) viewLastClickMaps[viewId]!! else 0L
        val span = current - lastClickTime
        if (span in 0..gap) {
            return true
        }
        viewLastClickMaps[viewId] = current
        return false
    }

    private fun getViewId(view: Any?): Int {
        return System.identityHashCode(view)
    }
}