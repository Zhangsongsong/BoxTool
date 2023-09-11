package com.zasko.boxtool.novel

import com.zasko.boxtool.components.HttpComponent
import com.zasko.boxtool.helper.LogUtil
import com.zasko.boxtool.novel.listener.HanYunHelper
import com.zasko.boxtool.novel.listener.NovelApi
import com.zasko.boxtool.selector.HanYunSelect
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import org.jsoup.Jsoup
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CopyOnWriteArraySet

object NovelManager {

    private const val TAG = "NovelManager"

    private lateinit var apiHelper: NovelApi
    private var isInitSuccess = false
    fun initHelper() {
        apiHelper = HanYunHelper()
        isInitSuccess = true
    }


    private val listeners = CopyOnWriteArraySet<NovelInitListener>()
    fun addInitListener(listener: NovelInitListener) {
        listeners.add(listener)
    }

    fun removeInitListener(listener: NovelInitListener) {
        listeners.remove(listener)
    }

    private fun isInit(): Boolean {
        return isInitSuccess
    }

    interface NovelInitListener {
        fun onSuccess()
    }


    fun checkInitAndCallback(callback: () -> Unit) {
        if (isInit()) {
            callback.invoke()
        } else {
            addInitListener(object : NovelInitListener {
                override fun onSuccess() {
                    callback.invoke()
                    removeInitListener(this)
                }
            })
        }
    }

    /**
     * 获取推荐列表
     */
    fun getRecommendList(callback: (List<RecommendListBean>) -> Unit): Disposable {
        return apiHelper.getRecommendList(callback)
    }
}