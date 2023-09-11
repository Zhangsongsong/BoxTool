package com.zasko.boxtool.novel.listener

import com.zasko.boxtool.novel.BookDetailBean
import com.zasko.boxtool.novel.RecommendListBean
import io.reactivex.rxjava3.disposables.Disposable

interface NovelApi {

    fun getRecommendList(callback: (List<RecommendListBean>) -> Unit): Disposable

    fun getBookDetail(url: String, callback: (BookDetailBean) -> Unit): Disposable? {
        return null
    }

}