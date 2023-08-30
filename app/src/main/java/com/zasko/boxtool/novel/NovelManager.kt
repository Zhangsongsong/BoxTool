package com.zasko.boxtool.novel

import android.annotation.SuppressLint
import com.zasko.boxtool.components.HttpComponent
import io.reactivex.rxjava3.core.Single

object NovelManager {


    private val novelServer by lazy {
        HttpComponent.createServer(NovelServer::class.java)
    }


    @SuppressLint("CheckResult")
    fun getRecommendList(): List<RecommendListBean> {
        novelServer.getHanYunHomeHtml().doOnSuccess {}.subscribe({}, {})

        return emptyList()

    }
}