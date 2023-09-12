package com.zasko.boxtool.utils

import android.content.Context

object FileUtils {


    fun loadFileByAssets(context: Context, fileName: String): String {
        val inputStream = context.assets.open(fileName)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer)
    }
}