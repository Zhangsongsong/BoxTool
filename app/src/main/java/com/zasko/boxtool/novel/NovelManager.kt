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
     * 当前存放的
     */

    private var currentBookDetailBean: BookDetailBean? = null
    fun updateCurrentBookDetailBean(bean: BookDetailBean) {
        currentBookDetailBean = bean
    }

    fun getCurrentBookDetailBean(): BookDetailBean? {
        return currentBookDetailBean
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

    fun getArticleDetail(url: String, callback: (String) -> Unit): Disposable? {
        return apiImpl.getArticleDetail(url, callback)
    }

    fun searchBook(key: String, callback: (List<SearchBookBean>) -> Unit): Disposable? {
        return apiImpl.searchBook(key, callback)
    }


}