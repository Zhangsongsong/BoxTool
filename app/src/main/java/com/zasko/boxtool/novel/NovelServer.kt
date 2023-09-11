package com.zasko.boxtool.novel

import com.zasko.boxtool.base.network.ResponseFormat
import com.zasko.boxtool.base.network.ResponseFormatConstant
import com.zasko.boxtool.selector.HanYunSelect
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface NovelServer {

    /**
     * 寒晕
     */
    @GET
    @ResponseFormat(value = ResponseFormatConstant.HTML)
    fun getHanYunHomeHtml(@Url url: String = HanYunSelect.BASE_URL): Single<String>

    @GET
    @ResponseFormat(value = ResponseFormatConstant.HTML)
    fun getBookDetail(@Url url: String = ""): Single<String>

}

data class RecommendListBean(var title: String = "", var img: String = "", var href: String = "")


data class BookDetailBean(
    var title: String = "",
    var img: String = "",
    var classify: String = "",
    var author: String = "",
    var status: String = "",
    var updateTime: String = "",
    var introduction: String = "",
    var articleList: List<ArticleBean> = emptyList()
)

data class ArticleBean(
    var href: String = "", var content: String = ""
)

