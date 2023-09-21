package com.zasko.boxtool.novel

import com.zasko.boxtool.MyApplication
import com.zasko.boxtool.selector.HanYunSelect
import com.zasko.boxtool.utils.FileUtils
import com.zasko.boxtool.utils.swiThread
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable

object HtmlConstants {


    const val HAN_YUN_HOME_HTML = "novel/home_html"

    const val HAN_YUN_BOOK_DETAIL_HTML = "novel/book_detail_html"

    const val HAN_YUN_ARTICLE_DETAIL = "novel/article_detail_html"


    const val HAN_YUN_SEARCH_HTML = "novel/search_book_html"


    fun getBookDetail(callback: (BookDetailBean) -> Unit): Disposable {
        return Single.just(FileUtils.loadFileByAssets(MyApplication.application, HAN_YUN_BOOK_DETAIL_HTML)).swiThread().doOnSuccess {
            callback.invoke(HanYunSelect.getBookDetailBean(it))
        }.subscribe({}, {})

    }
}