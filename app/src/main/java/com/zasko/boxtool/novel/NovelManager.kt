package com.zasko.boxtool.novel

import com.zasko.boxtool.novel.listener.HanYunImpl
import com.zasko.boxtool.novel.listener.NovelApi
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.CopyOnWriteArraySet

object NovelManager {

    private const val TAG = "NovelManager"

    private lateinit var apiImpl: NovelApi
    private var isInitSuccess = false
    fun initHelper() {
        apiImpl = HanYunImpl()
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
        return apiImpl.getRecommendList(callback)
    }

    fun getBookDetail(url: String, callback: (BookDetailBean) -> Unit): Disposable? {
        return apiImpl.getBookDetail(url, callback)
    }
}