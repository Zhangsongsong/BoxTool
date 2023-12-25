package com.zasko.boxtool.utils

import android.content.Context
import com.zasko.boxtool.helper.LogUtil
import java.io.File
import java.io.IOException

object FileUtils {


    fun loadFileByAssets(context: Context, fileName: String): String {
        val inputStream = context.assets.open(fileName)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer)
    }


    fun getVideoCacheDir(context: Context): File {
        val file = File(context.externalCacheDir, "video-cache")
        LogUtil.dPrintln("FileUtils getVideoCacheDir:${file.absolutePath}")
        return file
    }


    @Throws(IOException::class)
    private fun cleanDirectory(file: File) {
        if (!file.exists()) {
            return
        }
        val contentFiles = file.listFiles()
        if (contentFiles != null) {
            for (contentFile in contentFiles) {
                delete(contentFile)
            }
        }
    }

    @Throws(IOException::class)
    private fun delete(file: File) {
        if (file.isFile && file.exists()) {
            deleteOrThrow(file)
        } else {
            cleanDirectory(file)
            deleteOrThrow(file)
        }
    }

    @Throws(IOException::class)
    private fun deleteOrThrow(file: File) {
        if (file.exists()) {
            val isDeleted = file.delete()
            if (!isDeleted) {
                throw IOException(String.format("File %s can't be deleted", file.absolutePath))
            }
        }
    }
}