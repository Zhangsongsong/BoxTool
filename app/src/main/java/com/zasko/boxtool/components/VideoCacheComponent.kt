package com.zasko.boxtool.components

import android.app.Application
import android.net.Uri
import com.danikula.videocache.CacheListener
import com.danikula.videocache.HttpProxyCacheServer
import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.utils.FileUtils
import java.io.File

object VideoCacheComponent {

    private const val TAG = "VideoCacheComponent"

    private const val CACHE_MAX_COUNT = 20


    private lateinit var application: Application

    private val mProxy by lazy {
        HttpProxyCacheServer.Builder(application).cacheDirectory(FileUtils.getVideoCacheDir(application)).maxCacheFilesCount(CACHE_MAX_COUNT)
            .fileNameGenerator {
                val uri = Uri.parse(it)
                var fileName = it
                uri.path?.let { path ->
                    fileName = if (path.startsWith("/")) {
                        path.substring(1, path.indexOf("."))
                    } else {
                        path.substring(0, path.indexOf("."))
                    }
                }
                LogUtil.dPrintln("$TAG  fileNameGenerator videoUrl:${it} path:${uri.path} fileName:${fileName}")
                fileName
            }.build()
    }

    fun init(application: Application) {
        this.application = application
    }


    fun getProxyUrl(videoUrl: String): String {
        val url = mProxy.getProxyUrl(videoUrl)
        mProxy.registerCacheListener(CacheListenerImpl(), videoUrl)
        LogUtil.dPrintln("$TAG getProxyUrl videoUrl:${videoUrl} url:${url}")
        return url
    }

    private class CacheListenerImpl : CacheListener {
        override fun onCacheAvailable(cacheFile: File?, url: String?, percentsAvailable: Int) {
            LogUtil.dPrintln("$TAG onCacheAvailable percentsAvailable:${percentsAvailable} url:${url}")
            if (percentsAvailable >= 100) {
                mProxy.unregisterCacheListener(this)
            }
        }

    }
}

