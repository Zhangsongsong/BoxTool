package com.zasko.boxtool.helper

import android.util.Log

object LogUtil {

    private const val TAG = "LogUtil"

    fun dPrintln(msg: String) {
        Log.d(TAG, msg)
    }

    fun ePrintln(msg: String) {
        Log.e(TAG, msg)
    }

}