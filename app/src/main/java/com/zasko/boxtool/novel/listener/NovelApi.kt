package com.zasko.boxtool.novel.listener

import com.zasko.boxtool.novel.BookDetailBean
import com.zasko.boxtool.novel.RecommendListBean
import com.zasko.boxtool.novel.SearchBookBean
import io.reactivex.rxjava3.disposables.Disposable

interface NovelApi {

    fun getRecommendList(callback: (List<RecommendListBean>) -> Unit): Disposable

    fun getBookDetail(url: String, callback: (BookDetailBean) -> Unit): Disposable? {
        return null
    }

    fun getArticleDetail(url: String, callback: (String) -> Unit): Disposable? {
        return null
    }


    fun searchBook(key: String, callback: (List<SearchBookBean>) -> Unit): Disposable? {
        return null
    }

    fun downloadBook(bean: BookDetailBean): Disposable? {
        return null
    }
}