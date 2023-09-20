package com.zasko.boxtool.novel

import com.zasko.boxtool.base.network.ResponseFormat
import com.zasko.boxtool.base.network.ResponseFormatConstant
import com.zasko.boxtool.selector.HanYunSelect
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url
import java.io.Serializable

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


    @GET
    @ResponseFormat(value = ResponseFormatConstant.HTML)
    fun getArticleDetail(@Url url: String = ""): Single<String>


    @Headers(
        *arrayOf(
            "Accept-Language: zh-CN,zh;q=0.9,en;q=0.8,ko;q=0.7"
        )
    )
    @GET
    @ResponseFormat(value = ResponseFormatConstant.HTML)
    fun searchBook(@Url url: String = ""): Single<String>

}

data class RecommendListBean(var title: String = "", var img: String = "", var href: String = "") : Serializable


data class BookDetailBean(
    var title: String = "",
    var img: String = "",
    var classify: String = "",
    var author: String = "",
    var status: String = "",
    var updateTime: String = "",
    var introduction: String = "",
    var articleList: List<ArticleBean> = emptyList()
) : Serializable

data class ArticleBean(
    var href: String = "", var content: String = ""
) : Serializable


data class SearchBookBean(
    var title: String = "", var img: String = "", var author: String = "", var introduction: String = "", var href: String = ""
) : Serializable
