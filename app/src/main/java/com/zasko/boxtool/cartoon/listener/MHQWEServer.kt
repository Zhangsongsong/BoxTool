package com.zasko.boxtool.cartoon.listener

import com.zasko.boxtool.base.network.ResponseFormat
import com.zasko.boxtool.base.network.ResponseFormatConstant
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface MHQWEServer {

    @GET
    @ResponseFormat(value = ResponseFormatConstant.HTML)
    fun getHomePage(@Url url: String = MHQWEImpl.HOME_PAGE_URL): Single<String>
}