package com.zasko.boxtool.cartoon

import com.zasko.boxtool.cartoon.listener.CartoonApi
import com.zasko.boxtool.cartoon.listener.MHQWEImpl
import com.zasko.boxtool.helper.LogUtil
import io.reactivex.rxjava3.disposables.Disposable

object CartoonManager {

    private const val TAG = "CartoonManager"

    private lateinit var apiImpl: CartoonApi

    private var isInitSuccess = false

    fun initHelper() {
        apiImpl = MHQWEImpl()
        isInitSuccess = true
    }

    fun getHomePageList(): Disposable? {
        return apiImpl.getHomePageList()?.doOnSuccess { result ->
            LogUtil.dPrintln("$TAG getHomePageList result:${result}")
        }?.subscribe({}, { })
    }

}