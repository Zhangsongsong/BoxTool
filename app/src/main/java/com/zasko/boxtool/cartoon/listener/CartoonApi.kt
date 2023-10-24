package com.zasko.boxtool.cartoon.listener

import io.reactivex.rxjava3.core.Single

interface CartoonApi {

    fun getHomePageList(): Single<String>? {
        return null
    }
}