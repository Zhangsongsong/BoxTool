package com.zasko.boxtool.novel

import com.zasko.boxtool.base.network.ResponseFormat
import com.zasko.boxtool.base.network.ResponseFormatConstant
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface NovelServer {

    @GET
    @ResponseFormat(value = ResponseFormatConstant.HTML)
    fun getHanYunHomeHtml(@Url url: String = "https://www.hanyunzw.com/"): Single<String>

}

data class RecommendListBean(var title: String = "", var img: String = "")